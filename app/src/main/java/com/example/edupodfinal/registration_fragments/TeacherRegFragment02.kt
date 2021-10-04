package com.example.edupodfinal.registration_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.edupodfinal.R
import com.example.edupodfinal.TeacherRegActivity
import com.example.edupodfinal.databinding.FragmentTeacherReg02Binding
import com.example.edupodfinal.firebase.FirestoreClass
import com.example.edupodfinal.models.Teachers
import com.example.edupodfinal.models.User
import com.example.edupodfinal.util.Constants
import com.example.edupodfinal.util.getStringTrim
import com.example.edupodfinal.viewModels.TeachersViewModel

class TeacherRegFragment02 : Fragment() {

    private var _binding: FragmentTeacherReg02Binding? = null
    private val binding get() = _binding!!
    var userObject: User? = null

    var educaionZone: String? = null
    var subject: String? = null

    private val viewModel: TeachersViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTeacherReg02Binding.inflate(inflater, container, false)

        viewModel.userLive.observe(viewLifecycleOwner, Observer {
            Log.d("user_data", it.toString())
            userObject = it

        })

        setSchoolData()

        binding.btnNext.setOnClickListener {
            completingRegistration()
        }

        return binding.root

    }


    private fun setSchoolData() {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_items, Constants.educationZones)
        (binding.etEduzone as? AutoCompleteTextView)?.setAdapter(adapter)

        val adapter2 = ArrayAdapter(requireContext(), R.layout.list_items, Constants.subjects)
        (binding.etSubject as? AutoCompleteTextView)?.setAdapter(adapter2)

        binding.etEduzone.setOnItemClickListener { adapterView, view, i, l ->

            educaionZone = Constants.educationZones.get(i)

        }

        binding.etSubject.setOnItemClickListener { adapterView, view, i, l ->

            subject = Constants.subjects.get(i)

        }

    }

    private fun completingRegistration() {

        val user = User(
            userId = userObject?.userId,
            fullName = userObject?.fullName,
            userType = userObject!!.userType,
            regStatus = userObject!!.regStatus,
            schoolName = userObject!!.schoolName,
            schoolConId = userObject!!.schoolConId,
            address = binding.etAddress.getStringTrim(),
            admissionId = null,
            employeeId = binding.etEmpid.getStringTrim(),
            nic = binding.etNic.getStringTrim(),
            telephone = binding.etTelephone.getStringTrim()

        ).also {
            FirestoreClass().registerUser(this, it)
        }


    }


    fun successSecondStep() {

        val teacher = Teachers(

            userId = userObject?.userId,
            subjectName = subject,
            extraCaricular = binding.etExtra.getStringTrim(),
            educationZone = binding.etEduzone.getStringTrim()

        ).also {
            FirestoreClass().registerTeacher(this, it)
        }


    }

    fun successTeacherReg(){
        findNavController().navigate(TeacherRegFragment02Directions.actionTeacherRegFragment02ToTeacherRegFragment03())
    }


    fun failSecondStep() {
        Toast.makeText(
            requireContext(),
            "user colloection not created",
            Toast.LENGTH_SHORT
        ).show()
    }


}