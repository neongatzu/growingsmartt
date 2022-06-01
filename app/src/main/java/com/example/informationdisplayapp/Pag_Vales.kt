package com.example.informationdisplayapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.informationdisplayapp.databinding.ActivityPagPerfilBinding
import com.example.informationdisplayapp.databinding.ActivityPagValesBinding
import com.google.android.material.navigation.NavigationView
import com.google.common.net.InetAddresses.decrement
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Pag_Vales : AppCompatActivity() {
    //private lateinit var btnStart: Button
    private lateinit var pontos: TextView
    lateinit var mostrarPontos: Button
    lateinit var checkbox: RadioButton
    lateinit var checkbox2: RadioButton
    lateinit var checkbox3: RadioButton
    lateinit var refresh: ImageButton
    //private lateinit var auth: FirebaseAuth
    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var userID:String
    private lateinit var fAuth: FirebaseAuth
    private lateinit var binding: ActivityPagValesBinding

    val db = Firebase.firestore
    lateinit var code: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagValesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //var uid = auth.currentUser?.uid.toString()
        fAuth = FirebaseAuth.getInstance()

        //Menu lateral
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> startActivity(Intent(this,Pag_principal::class.java))
                R.id.nav_person -> startActivity(Intent(this, Pag_Perfil::class.java))
                R.id.nav_vales -> startActivity(Intent(this, Pag_Vales::class.java))
                R.id.nav_mapa -> startActivity(Intent(this, MapsActivity::class.java))
                R.id.nav_logout ->  startActivity(Intent(this, Pag_login::class.java))
            }
            true
        }



        pontos = findViewById(R.id.textViewPontos)
        mostrarPontos = findViewById(R.id.btnStart)
        refresh = findViewById(R.id.refresh)
        userID = fAuth.currentUser!!.uid


        refresh.setOnClickListener{
            finish()
            overridePendingTransition( 0, 0)
            startActivity(intent)
            overridePendingTransition( 0, 0)
        }
        mostrarPontos.setOnClickListener {
            val pontosRef = db.collection("utilizadores").document(userID)
            pontosRef.update("Pontos", FieldValue.increment(10))
        }

        trocarPontos()
        if (userID.isNotEmpty()) {
            getUserData()
        }
    }
    fun getUserData() {
        val db = Firebase.firestore
        //val user = FirebaseAuth.getInstance().currentUser

        db.collection("utilizadores").document(FirebaseAuth.getInstance().currentUser!!.uid)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val pontos: Any? = task.result.get("Pontos")
                    val utilizador: String? = task.result.getString("Utilizador")
                    binding.textViewPontos.text = pontos.toString()

                    //other stuff
                } else {
                    //deal with error
                }

            }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return  super.onOptionsItemSelected(item)
    }

    private fun trocarPontos() {
        checkbox = findViewById(R.id.checkBox)
        checkbox2 = findViewById(R.id.checkBox2)
        checkbox3 = findViewById(R.id.checkBox3)
        code = findViewById(R.id.btnQRCode)


        code.setOnClickListener{
            startActivity(Intent(this, Pag_QRCode::class.java))

        }
        checkbox.setOnClickListener {
            val pontosRef = db.collection("utilizadores").document(userID)
            pontosRef.update("Pontos", FieldValue.increment(-20))
        }
    }
}





