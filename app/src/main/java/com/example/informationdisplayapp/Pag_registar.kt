package com.example.informationdisplayapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Pag_registar : AppCompatActivity() {


    private lateinit var mEmailRegistar: TextInputEditText
    private lateinit var mPasswordRegistar: TextInputEditText
    private lateinit var mRegistarBt: Button
    private lateinit var mConfirmar: TextInputEditText

    private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore:FirebaseFirestore
    val db = Firebase.firestore

    private lateinit var userID:String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pag_registar)

        //mNome = findViewById(R.id.primeiroNome)
        //mUltimoNome = findViewById(R.id.ultimoNome)
        //mUtilizador = findViewById(R.id.Username)
        mEmailRegistar = findViewById(R.id.Email)
        mPasswordRegistar = findViewById(R.id.Palavra_passe)
        mRegistarBt = findViewById(R.id.criarConta)
        mConfirmar = findViewById(R.id.Confirmacao)
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        /*No caso de já estar registado, a aplicação abre na Homepage*/
        if (fAuth.currentUser != null) {
            startActivity(Intent(applicationContext, Pag_registar2::class.java))
            finish()
        }

        /*Ação do botão de registar
        * Qunado acionado o botão verifica se os parametros inseridos estão corretos para criar a conta na aplicação
        * No caso de o registo ser bem sucedido, o utilizador é envviado para a Pag_principal */
        mRegistarBt.setOnClickListener(View.OnClickListener {
            val email = mEmailRegistar.text.toString().trim { it <= ' ' }
            val password = mPasswordRegistar.text.toString().trim { it <= ' ' }
            val pontos = 0
            //Verifica se o email está presente
            if (TextUtils.isEmpty(email)) {
                mEmailRegistar.error = "Email necessário"
                return@OnClickListener
            }

            //Verifica se a password está presente
            if (TextUtils.isEmpty(password)) {
                mPasswordRegistar.error = "Password necessário"
                return@OnClickListener
            }
            //Verifica se a password tem mais ou igual de 6 caracteres
            if (password.length < 6) {
                mPasswordRegistar.error = "Password tem de ter mais de 6 caracteres "
                return@OnClickListener
            }

            //Registar o utilizador na firebase
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task -> // Se a conta do Utilizador for criada:
                if (task.isSuccessful) {
                    Toast.makeText(this@Pag_registar, "Utilizador Registado", Toast.LENGTH_SHORT).show()
                    userID = fAuth.currentUser!!.uid

                    val documentReference = fStore.collection("utilizadores").document(userID)

                    val user:MutableMap<String, Any> = hashMapOf()
                    user["Email"] = email


                    documentReference.set(user).addOnSuccessListener { Log.d(TAG, "onSuccess: user Profile is created for $userID") }.addOnFailureListener { e -> Log.d(TAG, "onFailure: $e")}
                    startActivity(Intent(applicationContext, Pag_registar::class.java))
                } else {
                    Toast.makeText(this@Pag_registar, "Registo do Utilizador Inválido" + task.exception, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    companion object {
        const val TAG = "TAG"
    }

    fun REGISTARLOGIN(view: View) {
        startActivity(Intent(this, Pag_registar2::class.java))
        finish()
    }
}
