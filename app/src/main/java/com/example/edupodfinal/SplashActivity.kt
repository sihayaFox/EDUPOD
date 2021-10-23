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


        val currentUserID = FirestoreClass().getCurrentUserID()

        if (currentUserID.isNotEmpty()) {
            FirestoreClass().getUserDetails(this)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
            }, 2500)
        }


    }


    fun navigateUser(user: User?) {
        user?.let {
            if (it.userType == 2) {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, TeachersActivity::class.java))
                    finish()
                }, 2500)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, StudentsActivity::class.java))
                    finish()
                }, 2500)
            }
        }
    }


}