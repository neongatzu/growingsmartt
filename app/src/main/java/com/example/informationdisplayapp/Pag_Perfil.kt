package com.example.informationdisplayapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.informationdisplayapp.databinding.ActivityPagPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference


class Pag_Perfil : AppCompatActivity() {
    private lateinit var binding: ActivityPagPerfilBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var fStore: FirebaseFirestore
    private lateinit var user: Utilizador
    private lateinit var uid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        uid = auth.currentUser?.uid.toString()

        //databaseReference = FirebaseDatabase.getInstance().getReference("utilizadores")


        if (uid.isNotEmpty()) {
            getUserData()
        }
        //getUserData()


    }

     fun getUserData() {
         val db = Firebase.firestore
         val user = FirebaseAuth.getInstance().currentUser

        db.collection("utilizadores").document(FirebaseAuth.getInstance().currentUser!!.uid)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val firstName: String? = task.result.getString("Nome")
                    val lastName: String? = task.result.getString("Ultimo Nome")
                    val email: String? = user?.email
                    val pontos: Any? = task.result.get("Pontos")
                    val utilizador: String? = task.result.getString("Utilizador")
                    binding.pontos.text = pontos.toString()
                    binding.nome.text = firstName + " " + lastName
                    binding.email.text = email

                    //other stuff
                } else {
                    //deal with error
                }
            }




        }
    }

