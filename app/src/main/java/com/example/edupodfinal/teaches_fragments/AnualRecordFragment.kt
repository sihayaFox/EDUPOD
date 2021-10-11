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
import com.example.edupodfinal.R
import com.example.edupodfinal.databinding.FragmentAnualRecordBinding
import com.example.edupodfinal.databinding.FragmentTermRecordFragmantBinding
import com.example.edupodfinal.firebase.FirestoreClass
import com.example.edupodfinal.models.AnualRecord
import com.example.edupodfinal.models.DailyRecord
import com.example.edupodfinal.util.Constants
import com.example.edupodfinal.util.getStringTrim
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class AnualRecordFragment : Fragment() {

    private var _binding: FragmentAnualRecordBinding? = null
    lateinit var datePickerTarget: MaterialDatePicker<Long>
    private val binding get() = _binding!!

    private var term:String? =null
    private var grade:String? =null
    private var competency:String? =null
    private var compLeval:String? =null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentAnualRecordBinding.inflate(inflater, container, false)

        val outputDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        datePickerTarget = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Start date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePickerTarget.addOnPositiveButtonClickListener {
            binding.etTargetDate.setText(outputDateFormat.format(it))
        }

        binding.etTargetDate.setOnClickListener {
            showTargetDatePicker()
        }

        binding.btnAnualRecAdd.setOnClickListener {
            createDailyRecord()
        }

        setSchoolData()


        return binding.root
    }

    private fun setSchoolData() {

        val adapterTerm = ArrayAdapter(requireContext(), R.layout.list_items, Constants.educationZones)
        (binding.termSpinner as? AutoCompleteTextView)?.setAdapter(adapterTerm)

        val adapterGrade = ArrayAdapter(requireContext(), R.layout.list_items, Constants.subjects)
        (binding.spinnerGrade as? AutoCompleteTextView)?.setAdapter(adapterGrade)

        val adapterCompetency = ArrayAdapter(requireContext(), R.layout.list_items, Constants.subjects)
        (binding.spinnerCopitency as? AutoCompleteTextView)?.setAdapter(adapterCompetency)

        val adapterCompLevel = ArrayAdapter(requireContext(), R.layout.list_items, Constants.subjects)
        (binding.spinnerComLevel as? AutoCompleteTextView)?.setAdapter(adapterCompLevel)

        binding.termSpinner.setOnItemClickListener { adapterView, view, i, l ->

            term = Constants.educationZones.get(i)

        }

        binding.spinnerCopitency.setOnItemClickListener { adapterView, view, i, l ->

            competency = Constants.subjects.get(i)

        }

        binding.spinnerComLevel.setOnItemClickListener { adapterView, view, i, l ->

            compLeval = Constants.subjects.get(i)

        }


        binding.spinnerGrade.setOnItemClickListener { adapterView, view, i, l ->

            grade = Constants.subjects.get(i)

        }

    }

    private fun createDailyRecord(){

        val anualRecord = AnualRecord(

            userId = FirestoreClass().getCurrentUserID(),
            term = binding.termSpinner.getStringTrim(),
            grade = binding.spinnerGrade.getStringTrim(),
            competency = binding.spinnerCopitency.getStringTrim(),
            competencyLevel = binding.spinnerComLevel.getStringTrim(),
            relevant_activity = binding.etActivities.getStringTrim(),
            targetDate = binding.etTargetDate.getStringTrim(),
            qualityInputs = binding.etQualityInput.getStringTrim(),
            assesment = binding.etAssesment.getStringTrim(),
            caricularActivity = binding.etCurrycular.getStringTrim(),
            proposedActivity =null,


        ).also {
            FirestoreClass().createAnnualRecord(this, it)
        }

    }

    private fun showTargetDatePicker() {
        datePickerTarget.show(requireActivity().supportFragmentManager, "Tag")
    }


    fun sucssesDailyRecord(){
        Toast.makeText(
            requireContext(),
            "daily record created",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun failSecondStep() {
        Toast.makeText(
            requireContext(),
            "term colloection not created",
            Toast.LENGTH_SHORT
        ).show()
    }


}