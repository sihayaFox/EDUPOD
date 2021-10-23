package com.example.edupodfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.edupodfinal.databinding.ActivityLoginBinding
import com.example.edupodfinal.firebase.FirestoreClass
import com.example.edupodfinal.models.User
import com.example.edupodfinal.util.Constants
import com.example.edupodfinal.util.getStringTrim
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

   private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtSignUp.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        binding.btnSignIn.setOnClickListener {
            signInWithUser()
        }
    }

    private fun signInWithUser(){

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            binding.etEmail.getStringTrim(),
            binding.etPassword.getStringTrim()
        ).addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    FirestoreClass().getUserDetails(this@LoginActivity)

                } else {
                    // Hide the progress dialog
                    Toast.makeText(
                        this,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
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