package com.example.edupodfinal.registration_fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.edupodfinal.R
import com.example.edupodfinal.TeacherRegActivity
import com.example.edupodfinal.databinding.FragmentTeacherReg01Binding
import com.example.edupodfinal.models.School
import com.example.edupodfinal.models.User
import com.example.edupodfinal.util.Constants
import com.example.edupodfinal.util.getStringTrim
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth


class TeacherRegFragmet01 : Fragment() {

    private var _binding: FragmentTeacherReg01Binding? = null
    private val binding get() = _binding!!
    private var school: School? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTeacherReg01Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSchoolData()

        binding.btnNext.setOnClickListener {
            registerUserDataAndNavigate()
        }

    }

    private fun setSchoolData() {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_items, Constants.schoolList)
        (binding.etSchool as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.etSchool.setOnItemClickListener { adapterView, view, i, l ->

            Log.d("schcool is:", Constants.schoolList.get(i).schoolConId.toString())

            school = Constants.schoolList.get(i)

        }




    }

    private fun registerUserDataAndNavigate() {

        val email: String = binding.email.getStringTrim()
        val password: String = binding.etPassword.getStringTrim()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                OnCompleteListener<AuthResult> { task ->

                    // If the registration is successfully done
                    if (task.isSuccessful) {

                        // Firebase registered user
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        // Instance of User data model class.
                        val user = User(
                            userId = firebaseUser.uid,
                            userType = 2,
                            regStatus = 1,
                            schoolName = school?.schoolName,
                            schoolConId = school?.schoolConId,
                            fullName = binding.etFullName.getStringTrim()

                        ).also {
                            val intent = Intent(requireActivity(), TeacherRegActivity::class.java)
                            intent.putExtra(Constants.EXTRA_USER_DETAILS, it)
                            startActivity(intent)
                        }

                    Log.d("message", "sucsessflly registered!")

                    } else {

                        // Hide the progress dialog
                        Toast.makeText(
                            requireContext(),
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()

                        Log.d("message", task.exception!!.message.toString())


                        // If the registering is not successful then show error message.
                    }
                })
    }


}

