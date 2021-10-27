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
import androidx.navigation.fragment.findNavController
import com.example.edupodfinal.R
import com.example.edupodfinal.TeacherRegActivity
import com.example.edupodfinal.databinding.FragmentStudentRegBinding
import com.example.edupodfinal.databinding.FragmentTeacherReg01Binding
import com.example.edupodfinal.firebase.FirestoreClass
import com.example.edupodfinal.models.School
import com.example.edupodfinal.models.User
import com.example.edupodfinal.util.Constants
import com.example.edupodfinal.util.getStringTrim
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class StudentRegFragment : Fragment() {

    private var _binding: FragmentStudentRegBinding? = null
    private val binding get() = _binding!!
    private var school: School? = null
    var user:User? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStudentRegBinding.inflate(inflater, container, false)



        setSchoolData()

        binding.btnSignUp.setOnClickListener {
            registerUserDataAndNavigate()
        }


        return binding.root
    }


    private fun setSchoolData() {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_items, Constants.schoolList)
        (binding.etSchool2 as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.etSchool2.setOnItemClickListener { adapterView, view, i, l ->

            Log.d("schcool is:", Constants.schoolList.get(i).schoolConId.toString())

            school = Constants.schoolList.get(i)

        }

    }

    private fun registerUserDataAndNavigate() {

        val email: String = binding.etEmail2.getStringTrim()
        val password: String = binding.etPassword.getStringTrim()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                OnCompleteListener<AuthResult> { task ->

                    // If the registration is successfully done
                    if (task.isSuccessful) {

                        // Firebase registered user
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        // Instance of User data model class.
                        user = User(
                            userId = firebaseUser.uid,
                            userType = 3,
                            regStatus = 1,
                            schoolName = school?.schoolName,
                            schoolConId = school?.schoolConId,
                            fullName = binding.etFullname.getStringTrim(),
                            admissionId = binding.etAdmition.getStringTrim()

                        ).also {
                            FirestoreClass().registerUser(this, it)

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


    fun succesStudentReg(){
        val intent = Intent(requireActivity(), TeacherRegActivity::class.java)
        intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
        startActivity(intent).also {
            requireActivity().finish()
        }
    }



}