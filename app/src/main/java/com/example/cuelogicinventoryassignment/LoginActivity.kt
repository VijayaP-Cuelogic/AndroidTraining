package com.example.cuelogicinventoryassignment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = SignUpActivity::class.qualifiedName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        buttonforgotPassword.setOnClickListener{

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
            builder.setView(view)
            val userName = findViewById<EditText?>(R.id.editText_userName)
            forgotPassword(userName)
            builder.setNegativeButton("Close", DialogInterface.OnClickListener { dialogInterface, i ->  })
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { dialogInterface, i ->  })
            builder.show()
        }
        buttonSignIn.setOnClickListener {
            signIn()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
      //  val currentUser = auth.currentUser
       // updateUI(currentUser)
    }
    private fun updateUI(currentUser : FirebaseUser?)
    {
        if (currentUser != null){
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }else
            {
                Toast.makeText(baseContext, "Please verify your email address.",
                    Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(baseContext, "Login failed.",
                Toast.LENGTH_SHORT).show()
        }
    }
     fun forgotPassword(userName: EditText?){
         val user = "vijpanchal@gmail.com"
         if (user.isEmpty()){
             return
         }
         if (!Patterns.EMAIL_ADDRESS.matcher(user).matches())
         {
             return
         }
         auth.sendPasswordResetEmail(user)
             .addOnCompleteListener { task ->
                 if (task.isSuccessful) {
                     Log.w(TAG, "Password link sent to this emailid")
                     Toast.makeText(baseContext, "Reset Password link sent to this emailid.",
                         Toast.LENGTH_SHORT).show()
                 }else
                 {
                     Log.w(TAG, "signInWithEmail:failure", task.exception)
                     Toast.makeText(baseContext, "Please try again.",
                         Toast.LENGTH_SHORT).show()
                 }
             }
     }
     private fun signIn(){
        if (editTextEmail.text.toString().isEmpty()){
            editTextEmail.error = "Please enter email"
            editTextEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches())
        {
            editTextEmail.error = "Please enter valid email"
            editTextEmail.requestFocus()
            return
        }
        if (editTextEnterPassword.text.toString().isEmpty()){
            editTextEnterPassword.error = "Please enter Password"
            editTextEnterPassword.requestFocus()
            return
        }
         auth.signInWithEmailAndPassword(editTextEmail.text.toString(), editTextEnterPassword.text.toString())
             .addOnCompleteListener(this) { task ->
                 if (task.isSuccessful) {
                     // Sign in success, update UI with the signed-in user's information
                     Log.d(TAG, "signInWithEmail:success")
                     val user = auth.currentUser
                     updateUI(user)
                 } else {
                     // If sign in fails, display a message to the user.
                     Log.w(TAG, "signInWithEmail:failure", task.exception)
                     Toast.makeText(baseContext, "Login failed.",
                         Toast.LENGTH_SHORT).show()
                     updateUI(null)
                     // ...
                 }
                 // ...
             }
    }
}