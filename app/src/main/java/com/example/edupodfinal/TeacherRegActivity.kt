package com.example.edupodfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.edupodfinal.databinding.ActivityRegistrationBinding
import com.example.edupodfinal.databinding.ActivityTeacherRegBinding
import com.example.edupodfinal.models.User
import com.example.edupodfinal.registration_fragments.TeacherRegFragment02
import com.example.edupodfinal.util.Constants
import com.example.edupodfinal.viewModels.TeachersViewModel

class TeacherRegActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeacherRegBinding

    private lateinit var navController: NavController

     var mUserDetails: User? =null

    private val viewModel: TeachersViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeacherRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()


        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            // Get the user details from intent as a ParcelableExtra.
            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!

            viewModel.setUserData(mUserDetails)


        }


    }

}