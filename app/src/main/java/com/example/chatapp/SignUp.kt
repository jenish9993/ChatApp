package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var showpassword: CheckBox
    private lateinit var btnSignup: Button
    private lateinit var btnLogin: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth= FirebaseAuth.getInstance()
        edtName=findViewById(R.id.edt_name)
        edtEmail=findViewById(R.id.edt_email)
        edtPassword=findViewById(R.id.edt_password)
        showpassword=findViewById(R.id.showpassword)
        btnSignup=findViewById(R.id.btnSignUp)
        btnLogin=findViewById(R.id.btnLogin)

        showpassword.setOnClickListener {
            if (showpassword.isChecked){
                edtPassword.inputType = 1
            }else{
                edtPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }


        btnSignup.setOnClickListener {
            val name=edtName.text.toString()
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()



            if (edtName.text.toString().trim().isNotEmpty() && edtEmail.text.toString().trim().isNotEmpty() && edtPassword.text.toString().trim().isNotEmpty()) {
                if (edtPassword.text.toString().length < 6) {
                    Toast.makeText(this, "Enter Minimum 6 Char ", Toast.LENGTH_SHORT).show()
                }
                else{
                    signUp(name,email,password)
                }
            }
            else {
                Toast.makeText(this, "Enter Signup Details ", Toast.LENGTH_SHORT).show()
            }



        }
        btnLogin.setOnClickListener {
            val intent = Intent(this,LogIn::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun signUp(name: String, email: String, password: String){

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)

                    val intent = Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {

                   Toast.makeText(this,"Some error Occurred",Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {

        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name,email,uid))

    }
}