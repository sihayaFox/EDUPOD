package com.example.edupodfinal.teaches_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.edupodfinal.R
import com.example.edupodfinal.databinding.FragmentTermRecordFragmantBinding
import com.example.edupodfinal.firebase.FirestoreClass
import com.example.edupodfinal.models.TermRecord
import com.example.edupodfinal.util.Constants
import com.example.edupodfinal.util.getStringTrim
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class TermRecordFragmant : Fragment() {

    private var _binding: FragmentTermRecordFragmantBinding? = null
    lateinit var datePickerTarget: MaterialDatePicker<Long>
    lateinit var datePickerCompleted: MaterialDatePicker<Long>
    private val binding get() = _binding!!

    private var term:String? =null
    private var peroids:String? =null
    private var competency:String? =null
    private var compLeval:String? =null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTermRecordFragmantBinding.inflate(inflater, container, false)

        val outputDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        datePickerTarget = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Start date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePickerCompleted = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select End date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePickerTarget.addOnPositiveButtonClickListener {
            binding.etTargetDate.setText(outputDateFormat.format(it))
        }

        datePickerCompleted.addOnPositiveButtonClickListener {
           binding.etCompletedDate.setText(outputDateFormat.format(it))
        }


        val adapterTerm = ArrayAdapter(requireContext(), R.layout.list_items, Constants.educationZones)
        (binding.spinnerTerm as? AutoCompleteTextView)?.setAdapter(adapterTerm)

        val adapterGrade = ArrayAdapter(requireContext(), R.layout.list_items, Constants.subjects)
        (binding.spinnerPeriods as? AutoCompleteTextView)?.setAdapter(adapterGrade)

        val adapterCompetency = ArrayAdapter(requireContext(), R.layout.list_items, Constants.subjects)
        (binding.spinnerCompetency as? AutoCompleteTextView)?.setAdapter(adapterCompetency)

        val adapterCompLevel = ArrayAdapter(requireContext(), R.layout.list_items, Constants.subjects)
        (binding.spinnerCompiLevel as? AutoCompleteTextView)?.setAdapter(adapterCompLevel)

        binding.spinnerTerm.setOnItemClickListener { adapterView, view, i, l ->

            term = Constants.educationZones.get(i)

        }

        binding.spinnerPeriods.setOnItemClickListener { adapterView, view, i, l ->

            peroids = Constants.subjects.get(i)

        }

        binding.spinnerCompetency.setOnItemClickListener { adapterView, view, i, l ->

            competency = Constants.subjects.get(i)

        }


        binding.spinnerCompiLevel.setOnItemClickListener { adapterView, view, i, l ->

            compLeval = Constants.subjects.get(i)

        }

        binding.etTargetDate.setOnClickListener {
            showTargetDatePicker()
        }

        binding.etCompletedDate.setOnClickListener {
            showCompleteDate()
        }

        binding.btnRecView.setOnClickListener {
            findNavController().navigate(TermRecordFragmantDirections.actionTermRecordFragmantToTermRecordViewFragment())
        }

        return binding.root
    }

    private fun showTargetDatePicker() {
        datePickerTarget.show(requireActivity().supportFragmentManager, "Tag")
    }

    private fun showCompleteDate() {
        datePickerCompleted.show(requireActivity().supportFragmentManager, "Tag")


        binding.btnAddRec.setOnClickListener {
            initiateRecord()
        }
    }

    private fun initiateRecord(){

        val termRecord = TermRecord(

            userId = FirestoreClass().getCurrentUserID(),
            term = binding.spinnerTerm.getStringTrim(),
            noOfPeriods = binding.spinnerPeriods.getStringTrim(),
            competency = binding.spinnerCompetency.getStringTrim(),
            competencyLevel = binding.spinnerCompiLevel.getStringTrim(),
            objectives = binding.etObjectives.getStringTrim(),
            activities = binding.etActivity.getStringTrim(),
            qualityInputs = binding.etQualityInput.getStringTrim(),
            targetDate = binding.etTargetDate.getStringTrim(),
            completedDate = binding.etCompletedDate.getStringTrim()
        ).also {
            FirestoreClass().createTermRecord(this,it)
        }


    }


    fun sucssesTermRecord(){

    }

    fun failSecondStep() {
        Toast.makeText(
            requireContext(),
            "term colloection not created",
            Toast.LENGTH_SHORT
        ).show()
    }



}