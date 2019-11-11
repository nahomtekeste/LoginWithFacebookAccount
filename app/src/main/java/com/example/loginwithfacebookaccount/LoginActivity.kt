package com.example.loginwithfacebookaccount

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()


        // this code allow the user to get to login
        btn_login.setOnClickListener {
            doLogin()
        }


    }

    private fun doLogin() {
        if (txt_name.text.toString().isEmpty()) {
            txt_name.error = "Please enter email"
            txt_name.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(txt_name.text.toString()).matches()) {
            txt_name.error = "Please enter valid email"
            txt_name.requestFocus()
            return
        }

        if (txt_password.text.toString().isEmpty()) {
            txt_password.error = "Please enter password"
            txt_password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(txt_name.text.toString(), txt_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {

                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
// this funtion here allow the user to vification proces in firbase  virefivation and if it's okey go to mainactivity
    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    baseContext, "Please verify your email address.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                baseContext, "Login failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}