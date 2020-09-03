package com.example.cuelogicinventoryassignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        buttonSignIn.setOnClickListener {
            validateUser()
            auth.signInWithEmailAndPassword(editTextEmail.text.toString(), editTextEnterPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Login failed.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                        // ...
                    }
                    // ...
                }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    private fun updateUI(currentUser : FirebaseUser?)
    {
        if (currentUser != null){
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }else{
            Toast.makeText(baseContext, "Login failed.",
                Toast.LENGTH_SHORT).show()
        }
    }
     private fun validateUser(){
        if (editTextEmail.text.toString().isEmpty()){
            editTextEmail.error = "Please enter email"
            editTextEmail.requestFocus()
            return
        }
//        if (Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches())
//        {
//            editTextEmail.error = "Please enter valid email"
//            editTextEmail.requestFocus()
//            return
//        }
        if (editTextEnterPassword.text.toString().isEmpty()){
            editTextEnterPassword.error = "Please enter Password"
            editTextEnterPassword.requestFocus()
            return
        }
    }
}