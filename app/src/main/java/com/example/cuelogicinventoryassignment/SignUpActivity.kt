package com.example.cuelogicinventoryassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import android.widget.Toast.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        buttonSignUp.setOnClickListener {
           signUpUser()
        }
        btnSignIn.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
    private fun signUpUser(){
        if (editTextEmailid.text.toString().isEmpty()){
            editTextEmailid.error = "Please enter email"
            editTextEmailid.requestFocus()
            return
        }
//        if (Patterns.EMAIL_ADDRESS.matcher(editTextEmailid.text.toString()).matches())
//        {
//            editTextEmailid.error = "Please enter valid email"
//            editTextEmailid.requestFocus()
//            return
//        }
        if (editTextPassword.text.toString().isEmpty()){
            editTextPassword.error = "Please enter Password"
            editTextPassword.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(editTextEmailid.text.toString(), editTextPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                print("Email sent.")
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                        }
                } else {
                    // If sign in fails, display a message to the user.
                    makeText(baseContext, "Sign Up failed.Please try again later!", LENGTH_SHORT).show()
                }
            }
    }
}