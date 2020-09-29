package ui.auth

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog.Builder
import androidx.appcompat.app.AppCompatActivity
import com.example.cuelogicinventoryassignment.ActivityAllDeviceListView
import ui.home.DashboardActivity
import com.example.cuelogicinventoryassignment.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var auth: FirebaseAuth
    lateinit var ref : DatabaseReference
    private val TAG = SignUpActivity::class.qualifiedName
    private lateinit var user_type: String
    var isAdmin = false
    lateinit var adminEmailsArray: ArrayList<String>
    lateinit var progressDialog : ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Login")
        progressDialog.setMessage("User Login, please wait")

        val sharedPreference =  getSharedPreferences("kotlinsharedpreference", Context.MODE_PRIVATE)
        user_type = sharedPreference.getString("user_type","").toString()
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        adminEmailsArray = arrayListOf<String>()
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

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
           // finish()
        }
        buttonforgotPassword.setOnClickListener{

            val isConnected = isAvailableNetwork(this)
            if (isConnected) {
                val builder = Builder(this)
                builder.setTitle("Forgot Password")
                val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
                builder.setView(view)
                val userName = view.findViewById<EditText?>(R.id.editText_userName)

                builder.setNegativeButton(
                    "Close",
                    DialogInterface.OnClickListener { dialogInterface, i -> })
                builder.setPositiveButton(
                    "Reset",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        if (userName != null) {
                            if (userName.text.isNotEmpty()) {
                                var email = userName?.text.toString()
                                auth.sendPasswordResetEmail(email)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.w(TAG, "Password link sent to this email id")
                                            showAlert("Reset Password link sent to email id")

                                        } else {
                                            Log.w(TAG, "signInWithEmail:failure", task.exception)

                                            var message = task.exception!!.message.toString()
                                            showAlert(message)

                                        }
                                    }
                            }
                        }
                    })
                builder.show()
            }else
                showAlert("No internet connection, please try again")
        }
        buttonSignIn.setOnClickListener {
            val isConnected = isAvailableNetwork(this)
            if (isConnected) {
                signIn()
            }else{
                showAlert("No internet connection,please try again")
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }
    private fun updateUI(currentUser : FirebaseUser?)
    {
        getUserDetails(currentUser)
        if (currentUser != null){
            if (currentUser.isEmailVerified) {

                if (user_type.equals("admin")) {
                    val intent = Intent(this@LoginActivity, ActivityAllDeviceListView::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.putExtra("Exit",true)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.putExtra("Exit",true)
                    startActivity(intent)
                    finish()
                }
            }else
            {
                showAlert("Please verify your email address.")
            }
        }
        else{
            showAlert("Login Failed, Please try again...")

        }
    }
    fun getUserDetails(currentUser : FirebaseUser?){
        var userID = currentUser?.uid.toString()
        var email = currentUser?.email.toString()
        ref = FirebaseDatabase.getInstance().getReference("users/"+user_type)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists()) {
                      for (d in p0.children) {
                          val emailId = d.child("email").getValue().toString()
                          val name = d.child("name").getValue().toString()
                          if (emailId.equals(email,true)) {
                              val sharedPreference =
                                  getSharedPreferences(
                                      "kotlinsharedpreference",
                                      Context.MODE_PRIVATE
                                  )
                              var editor = sharedPreference.edit()
                              editor.putString("user_name", name)
                              editor.commit()
                          }
                      }
                }
            }
        })
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
         if (user_type.equals("admin",true)){
             for (i in adminEmailsArray){
                 if (editTextEmail.text.toString().equals(i.toString())){
                     isAdmin = true;
                 }
             }
             if (isAdmin == false){
                 editTextEmail.error = "Please enter valid admin email"
                 editTextEmail.requestFocus()
                 return
             }
         }
         progressDialog.show()
         auth.signInWithEmailAndPassword(editTextEmail.text.toString(), editTextEnterPassword.text.toString())
             .addOnCompleteListener(this) { task ->
                 if (task.isSuccessful) {
                     // Sign in success, update UI with the signed-in user's information
                     Log.d(TAG, "signInWithEmail:success")
                     val user = auth.currentUser
                     getUserDetails(user)
                     progressDialog.dismiss()
                     updateUI(user)
                 } else {
                     // If sign in fails, display a message to the user.
                     Log.w(TAG, "signInWithEmail:failure", task.exception)
                     var message = task.exception!!.message.toString()
                     progressDialog.dismiss()
                     showAlert(message)
                     // ...
                 }
                 // ...
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
    fun showAlert( message: String){
        val builder = Builder(this)
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