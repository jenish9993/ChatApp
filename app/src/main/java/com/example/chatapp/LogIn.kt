package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var showpwd:CheckBox
    private lateinit var txtForgot:TextView
    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        mAuth= FirebaseAuth.getInstance()

        edtEmail=findViewById(R.id.edt_email)
        edtPassword=findViewById(R.id.edt_password)
        showpwd=findViewById(R.id.showpwd)
        txtForgot=findViewById(R.id.txtForgot)
        btnLogin=findViewById(R.id.btnLogin)
        btnSignup=findViewById(R.id.btnSignUp)

        showpwd.setOnClickListener {
            if (showpwd.isChecked){
                edtPassword.inputType = 1
            }else{
                edtPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }

        txtForgot.setOnClickListener {
            val intent = Intent(this,ForgotPassword::class.java)
            finish()
            startActivity(intent)
        }



        btnSignup.setOnClickListener {
            val intent = Intent(this,SignUp::class.java)
            finish()
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            if (edtEmail.text.toString().trim().isNotEmpty() && edtPassword.text.toString().trim().isNotEmpty()) {
                login(email,password)
            } else {
                Toast.makeText(this, "Enter Email and Password ", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun login(email: String, password: String){

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent=Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this,"User does Not exist",Toast.LENGTH_SHORT).show()
                }
            }

    }
}