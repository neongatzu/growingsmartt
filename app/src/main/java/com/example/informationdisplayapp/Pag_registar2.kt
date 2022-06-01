package com.example.informationdisplayapp


import android.content.Intent
import android.net.Uri
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

import com.example.informationdisplayapp.databinding.ActivityPagRegistar2Binding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class Pag_registar2: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference
    private lateinit var imageUri: Uri
    private lateinit var fStore:FirebaseFirestore

    private lateinit var binding: ActivityPagRegistar2Binding
    private lateinit var mConfirmar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPagRegistar2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        mConfirmar = findViewById(R.id.criarConta2)


        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("utilizadores")

        mConfirmar.setOnClickListener(View.OnClickListener {  })

        binding.criarConta2.setOnClickListener {
            val mNome = binding.primeiroNome.text.toString()
            val mUltimoNome = binding.ultimoNome.text.toString()
            val mUtilizador = binding.Username.text.toString()
            val mPontos = 0
            //val mId = auth.currentUser?.uid

            //val user = Utilizador(mNome,mUltimoNome,mUtilizador,mPontos,mId)

            val documentReference = fStore.collection("utilizadores").document(uid!!)

            val user:MutableMap<String, Any> = hashMapOf()
                    user["Nome"] = mNome
                    user["Pontos"] = mPontos
                    user["Ultimo Nome"] = mUltimoNome
                    user["Username"] = mUtilizador


                    documentReference.set(user).addOnSuccessListener { Log.d(Pag_registar.TAG, "onSuccess: user Profile is created for $uid") }.addOnFailureListener { e -> Log.d(
                        Pag_registar.TAG, "onFailure: $e")}
            startActivity(Intent(applicationContext, Pag_principal::class.java))

            }


            /*if (uid!= null){
                databaseReference.child(uid).setValue(user).addOnCompleteListener{
                    if (it.isSuccessful){
                        uploadProfilePic()
                    }
                }
            }
            startActivity(Intent(applicationContext, Pag_login::class.java))
        }*/

    }


}
