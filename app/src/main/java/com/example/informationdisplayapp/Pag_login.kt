package com.example.informationdisplayapp


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Pag_login : AppCompatActivity() {

    private lateinit var mUsernameLogin: TextInputEditText
    private lateinit var mPasswordLogin: TextInputEditText
    private lateinit var mLoginBt: MaterialButton
    private lateinit var mLoginRegistar: TextView
    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pag_login)

        mUsernameLogin = findViewById(R.id.editTextUsernameLogin)
        mPasswordLogin = findViewById(R.id.editTextPasswordLogin)
        mLoginBt = findViewById(R.id.botaoLogin)
        mLoginRegistar = findViewById(R.id.botaoRegisto)
        FirebaseAuth.getInstance().signOut()
        fAuth = FirebaseAuth.getInstance()


        /*No caso do utilizador necessitar de registar uma nova conta a partir do login*/
        mLoginRegistar.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, Pag_registar::class.java))
        })

        /*Ação do botão de Login
        * Após os dados de login serem inseridos e verificados, o utilizador passa para a Homepage*/
        mLoginBt.setOnClickListener(View.OnClickListener {
          val email = mUsernameLogin.getText().toString().trim { it <= ' ' }
            val password = mPasswordLogin.getText().toString().trim { it <= ' ' }

            //Verifica se o email está presente
            if (TextUtils.isEmpty(email)) {
                mUsernameLogin.error = "Username Necessário"
                return@OnClickListener
            }

            //Verifica se a password está presente
            if (TextUtils.isEmpty(password)) {
                mPasswordLogin.error = "Password necessário"
                return@OnClickListener
            }

            //Verifica se a password tem mais ou igual de 6 caracteres
            if (password.length < 6) {
                mPasswordLogin.error = "Password tem de ter mais de 6 caracteres "
                return@OnClickListener
            }

            //Sign In
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task -> // Se a conta do Utilizador for criada:
                if (task.isSuccessful) { Toast.makeText(this@Pag_login , "Log In realizado com sucesso", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, Pag_principal::class.java))
                } else {
                    Toast.makeText(this@Pag_login, "Erro no Log In" + task.exception, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    }


