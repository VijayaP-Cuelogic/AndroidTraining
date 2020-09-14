package com.example.cuelogicinventoryassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toast.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.dialog_forgot_password.*
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val TAG = SignUpActivity::class.qualifiedName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        buttonSignUp.setOnClickListener {
           signUpUser()
        }
        btnSignIn.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
           // finish()
        }
    }
    private fun signUpUser(){

        if (editTextCueId.text.toString().isEmpty()){
            editTextCueId.error = "Please enter Cue Id"
            editTextCueId.requestFocus()
            return
        }
        if (editTextName.text.toString().isEmpty()){
            editTextName.error = "Please enter Name"
            editTextName.requestFocus()
            return
        }
        if (editTextEmailid.text.toString().isEmpty()){
            editTextEmailid.error = "Please enter email"
            editTextEmailid.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmailid.text.toString()).matches())
        {
            editTextEmailid.error = "Please enter valid email"
            editTextEmailid.requestFocus()
            return
        }
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
                                var employee = Employee(editTextName.text.toString(), editTextCueId.text.toString(), editTextEmailid.text.toString())
                                var ref = FirebaseDatabase.getInstance().getReference("users/employee")

                                var empId = ref.push().key
                                ref.child(empId!!).setValue(employee).addOnCompleteListener{
                                    Log.d(TAG, "user record save in db")
                                }
                                Log.d(TAG, "createUserWithEmail:success")
                                startActivity(Intent(this, LoginActivity::class.java))
                               // finish()
                            }
                        }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    makeText(baseContext, "Sign Up failed.Please try again later!", LENGTH_SHORT).show()
                }
            }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}