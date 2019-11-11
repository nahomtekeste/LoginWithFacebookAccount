package com.example.loginwithfacebookaccount

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // this allow user to get the login pages
        btn_login.setOnClickListener {
            var intent = Intent(applicationContext, LoginActivity::class.java)
            startActivities(arrayOf(intent))
        }
        // this code allow the user to get to the sign up pages
        btn_signup.setOnClickListener {
            var intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }
        // this funtion allow the user to get to the home pages
         fun showHome(){
            var intent = Intent (applicationContext , HomeActivity::class.java)
            startActivities(arrayOf(intent))
        }

        // this code allow the user to get in account using facebook account
        btn_facebook.setOnClickListener {
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d("letsSee", "Facebook token: " + loginResult.accessToken.token)
                        showHome()
                    }

                    override fun onCancel() {
                        Log.d("letsSee", "Facebook onCancel.")

                    }

                    override fun onError(error: FacebookException) {
                        Log.d("letsSee", "Facebook onError.")

                    }
                })

        }
    }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            callbackManager?.onActivityResult(requestCode, resultCode, data)

            Log.d("letsSee", "malsehnnnnnn: " + data)
        }


    }

