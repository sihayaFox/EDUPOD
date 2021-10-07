package com.example.edupodfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.edupodfinal.firebase.FirestoreClass
import com.example.edupodfinal.models.User

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        FirestoreClass().getUserDetails(this)



    }

     fun checkExistingUser(user: User?){


        Handler(Looper.getMainLooper()).postDelayed({

            if (FirestoreClass().getCurrentUserID()?.isNotEmpty() == true) {
                // Launch dashboard screen.
                if (user?.userType == 2){
                    startActivity(Intent(this@SplashActivity, TeachersActivity::class.java)).also {
                        finish()
                    }
                }

            } else {
                // Launch the Login Activity
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }

        }, 2500)

    }


}