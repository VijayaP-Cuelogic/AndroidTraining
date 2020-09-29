package ui.auth

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.example.cuelogicinventoryassignment.Employee
import com.example.cuelogicinventoryassignment.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val TAG = SignUpActivity::class.qualifiedName
    private lateinit var user_type: String
    lateinit var ref : DatabaseReference
    var isAdmin = false
    lateinit var progressDialog : ProgressDialog
    lateinit var adminEmailsArray: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Sign In")
        progressDialog.setMessage("SignIn in progress, please wait")

        adminEmailsArray = arrayListOf<String>()
        val sharedPreference =  getSharedPreferences("kotlinsharedpreference", Context.MODE_PRIVATE)
        user_type = sharedPreference.getString("user_type","").toString()
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        ref = FirebaseDatabase.getInstance().getReference("AdminAccess")
        ref.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                adminEmailsArray = arrayListOf<String>()
                if (p0.exists()){

                    p0.children.forEachIndexed { index, element ->
                        val emailId = p0.child("admin"+index).getValue().toString()
                        adminEmailsArray.add(emailId)
                    }
                }
            }
        })

        buttonSignUp.setOnClickListener {
            val isConnected = isAvailableNetwork(this)
            if (isConnected){
                signUpUser()
            }else{
                showAlert("No internet, Please try again")
            }
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

        if (user_type.equals("admin",true)){
            for (i in adminEmailsArray){
                if (editTextEmailid.text.toString().equals(i.toString())){
                    isAdmin = true;
                }
            }
            if (isAdmin == false){
                showAlert("Please enter valid admin email address")
                return
            }
        }
        progressDialog.show()
        auth.createUserWithEmailAndPassword(editTextEmailid.text.toString(), editTextPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                print("Email sent.")
                                var employee = Employee(editTextName.text.toString(), editTextCueId.text.toString(), editTextEmailid.text.toString())
                                var ref = FirebaseDatabase.getInstance().getReference("users/"+user_type.toString())

                                var empId = ref.push().key
                                ref.child(empId!!).setValue(employee).addOnCompleteListener{
                                    Log.d(TAG, "user record save in db")
                                }
                                progressDialog.dismiss()
                                Log.d(TAG, "createUserWithEmail:success")
                                startActivity(Intent(this, LoginActivity::class.java))
                               // finish()
                            }
                        }
                } else {
                    progressDialog.dismiss()
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    var message = task.exception!!.message.toString()
                    showAlert(message)
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
    fun showAlert(message: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(message)
        builder.setNegativeButton("Close", DialogInterface.OnClickListener { dialogInterface, i ->  })
        builder.show()
    }
    fun isAvailableNetwork(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                //It will check for both wifi and cellular network
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }
}