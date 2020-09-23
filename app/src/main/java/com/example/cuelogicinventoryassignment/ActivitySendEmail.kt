package com.example.cuelogicinventoryassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*

class ActivitySendEmail : AppCompatActivity() {
    lateinit var editTextMail: EditText
    lateinit var editTextSubject: EditText
    lateinit var editTextMessage: EditText
    lateinit var buttonSend: Button
    lateinit var email: String
    lateinit var subject: String
    lateinit var message: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_email)
        title = "KotlinApp"
        editTextMail = findViewById(R.id.editTextMail)
        editTextSubject = findViewById(R.id.editTextSubject)
        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)
        buttonSend.setOnClickListener {
            if (editTextMail.text.toString().isEmpty()){
                editTextMail.error = "Please enter email"
                editTextMail.requestFocus()
                return@setOnClickListener
            }
            if (editTextSubject.text.toString().isEmpty()){
                editTextSubject.error = "Please enter subject"
                editTextSubject.requestFocus()
                return@setOnClickListener
            }
            email = editTextMail.text.toString()
            subject = editTextSubject.text.toString()
            message = editTextMessage.text.toString()

            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, email)
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Select email"))
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