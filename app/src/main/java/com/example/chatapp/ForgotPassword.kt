package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {


    private lateinit var edtForgot: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        edtForgot=findViewById(R.id.edtForgotEmail)
        btnLogin=findViewById(R.id.btnLogin)
        btnSubmit=findViewById(R.id.btnsubmit)


        btnSubmit.setOnClickListener {

            val email:String = edtForgot.text.toString().trim() { it<= ' ' }
            if(email.isEmpty()){
                Toast.makeText(this,"Please Enter Email Address",Toast.LENGTH_LONG).show()
            }
            else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Reset Link is Successfully sent ",
                                Toast.LENGTH_LONG
                            ).show()
                        }else{
                            Toast.makeText(
                                this,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }

        }

        btnLogin.setOnClickListener {
            val intent = Intent(this,LogIn::class.java)
            finish()
            startActivity(intent)
        }
    }
}