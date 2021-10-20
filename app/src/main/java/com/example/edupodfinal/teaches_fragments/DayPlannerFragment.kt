package com.example.edupodfinal.teaches_fragments

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
import com.example.edupodfinal.databinding.FragmentDayPlannerBinding
import com.example.edupodfinal.firebase.FirestoreClass
import com.example.edupodfinal.models.DailyPlanner
import com.example.edupodfinal.models.DailyRecord
import com.example.edupodfinal.util.Constants
import com.example.edupodfinal.util.getStringTrim
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*


class DayPlannerFragment : Fragment() {

    private var _binding: FragmentDayPlannerBinding? = null
    private val binding get() = _binding!!
    lateinit var datePickerDate: MaterialDatePicker<Long>
    lateinit var datePickerCompleted: MaterialDatePicker<Long>
    private var className:String? = null
    private var subjectName:String? = null
    private var dateText:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDayPlannerBinding.inflate(inflater, container, false)

        val outputDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        datePickerDate = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Start date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePickerDate.addOnPositiveButtonClickListener{
            binding.btnDate.setText(outputDateFormat.format(it))
            binding.txtDay.setText(outputDateFormat.format(it))

            val date = SimpleDateFormat("EEEE").format(it)

            dateText = date


        }


        setCalsses()
        setSubjects()

        binding.btnDate.setOnClickListener {
            showTargetDatePicker()
        }

        binding.etStartTime.setOnClickListener {
            showStartTimePicker()
        }

        binding.etEndTime.setOnClickListener {
            showEndTimePicker()
        }

        binding.btnAddDalyPlanner.setOnClickListener {
            createDayPlanner()
        }


        return binding.root

    }

    private fun showTargetDatePicker() {
        datePickerDate.show(requireActivity().supportFragmentManager, "Tag")
    }

    private fun showStartTimePicker() {
        val timePickerStart =
            MaterialTimePicker.Builder()
                .setTitleText("Select Time")
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(10)
                .build()

        timePickerStart.show(requireActivity().supportFragmentManager, "Tag")

        timePickerStart.addOnPositiveButtonClickListener {

            val formatTime = SimpleDateFormat("H:mm", Locale.getDefault()).parse("${timePickerStart.hour}:${timePickerStart.minute}")
            val fromTime = SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(formatTime!!)

            binding.etStartTime.setText(fromTime)

        }
    }

    private fun showEndTimePicker() {

        val timePickerEnd =
            MaterialTimePicker.Builder()
                .setTitleText("Select Time")
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(10)
                .build()

        timePickerEnd.show(requireActivity().supportFragmentManager, "Tag")

        timePickerEnd.addOnPositiveButtonClickListener {

            val formatTime = SimpleDateFormat("H:mm", Locale.getDefault()).parse("${timePickerEnd.hour}:${timePickerEnd.minute}")
            val fromTime = SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(formatTime!!)

            binding.etEndTime.setText(fromTime)

        }
    }

    private fun setCalsses() {

        val adapter = ArrayAdapter(requireContext(), R.layout.list_items, Constants.subjects)
        (binding.etClass as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.etClass.setOnItemClickListener { adapterView, view, i, l ->

            className = Constants.educationZones.get(i)

        }
    }

    private fun setSubjects() {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_items, Constants.subjects)
        (binding.etSubject as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.etSubject.setOnItemClickListener { adapterView, view, i, l ->

            subjectName = Constants.educationZones.get(i)

        }
    }

    private fun createDayPlanner(){

        val dailyPlanner = DailyPlanner(

            userId = FirestoreClass().getCurrentUserID(),
            date = binding.txtDay.text.toString(),
            startTime = binding.etStartTime.getStringTrim(),
            endTime = binding.etEndTime.getStringTrim(),
            className = binding.etClass.getStringTrim(),
            subjectName = binding.etSubject.getStringTrim(),
            details = binding.etDetails.getStringTrim(),
            dayInText = dateText

        ).also {
            FirestoreClass().createDayPlanner(this, it)
        }

    }

    fun sucssesDailyPlanner(){
        Toast.makeText(
            requireContext(),
            "daily planner created",
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