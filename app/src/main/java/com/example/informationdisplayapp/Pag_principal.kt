package com.example.informationdisplayapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.qrcode.encoder.QRCode

class Pag_principal : AppCompatActivity() {

    private lateinit var Mapa: Button
    private lateinit var Logout: Button
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var itemList: ArrayList<item>
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pag_principal)

        //Menu Lateral
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> Toast.makeText(
                    applicationContext,
                    "You're already here!",
                    Toast.LENGTH_LONG
                ).show()
                R.id.nav_person -> startActivity(Intent(this, Pag_Perfil::class.java))
                R.id.nav_vales -> startActivity(Intent(this, Pag_Vales::class.java))
                R.id.nav_mapa -> startActivity(Intent(this, MapsActivity::class.java))
                R.id.nav_logout -> startActivity(Intent(this, Pag_login::class.java))
            }
            true
        }
        itens()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun itens() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemList = ArrayList()

        itemList.add(item(R.drawable.saco2, "Saco c/ árvores"))
        itemList.add(item(R.drawable.saco1, "Saco c/ contentores"))
        itemList.add(item(R.drawable.poopsans, "Saco p/ passeio"))
        itemList.add(item(R.drawable.vela, "vela aromatica"))

        itemAdapter = ItemAdapter(itemList)
        recyclerView.adapter = itemAdapter

        itemAdapter.onItemClick = {
            val intent = Intent(this, DetailedActivity::class.java)
            intent.putExtra("item", it)
            startActivity(intent)

        }
    }
}

