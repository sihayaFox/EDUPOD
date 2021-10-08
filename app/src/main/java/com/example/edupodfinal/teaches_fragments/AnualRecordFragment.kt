package com.example.edupodfinal.teaches_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.example.edupodfinal.R
import com.example.edupodfinal.databinding.FragmentAnualRecordBinding
import com.example.edupodfinal.databinding.FragmentTermRecordFragmantBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class AnualRecordFragment : Fragment() {

    private var _binding: FragmentAnualRecordBinding? = null
    lateinit var datePickerTarget: MaterialDatePicker<Long>
    private val binding get() = _binding!!


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

        val listenerTerm: AdapterView.OnItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("selected_Term", parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.termSpinner.onItemSelectedListener = listenerTerm

        val listenerCompetencyLevel: AdapterView.OnItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("selected_comp_level", parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.spinnerComLevel.onItemSelectedListener = listenerCompetencyLevel

        val listenerNoPeriods: AdapterView.OnItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("selected_periods", parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.spinnerGrade.onItemSelectedListener = listenerNoPeriods


        val listenerCompetency: AdapterView.OnItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("selected_comp", parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.spinnerCopitency.onItemSelectedListener = listenerCompetency


        binding.etTargetDate.setOnClickListener {
            showTargetDatePicker()
        }


        return binding.root
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