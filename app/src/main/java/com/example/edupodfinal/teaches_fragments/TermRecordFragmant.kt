package com.example.edupodfinal.teaches_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.example.edupodfinal.databinding.FragmentTermRecordFragmantBinding
import com.example.edupodfinal.firebase.FirestoreClass
import com.example.edupodfinal.models.TermRecord
import com.example.edupodfinal.util.getStringTrim
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class TermRecordFragmant : Fragment() {

    private var _binding: FragmentTermRecordFragmantBinding? = null
    lateinit var datePickerTarget: MaterialDatePicker<Long>
    lateinit var datePickerCompleted: MaterialDatePicker<Long>
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        val listenerTerm: AdapterView.OnItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("selected_Term", parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.spinnerTerm.onItemSelectedListener = listenerTerm

        val listenerCompetencyLevel: AdapterView.OnItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("selected_comp_level", parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.spinnerCompiLevel.onItemSelectedListener = listenerCompetencyLevel

        val listenerNoPeriods: AdapterView.OnItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("selected_periods", parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.spinnerPeriods.onItemSelectedListener = listenerNoPeriods


        val listenerCompetency: AdapterView.OnItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("selected_comp", parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.spinnerCompetency.onItemSelectedListener = listenerCompetency

        binding.etTargetDate.setOnClickListener {
            showTargetDatePicker()
        }

        binding.etCompletedDate.setOnClickListener {
            showCompleteDate()
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