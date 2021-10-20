package com.example.edupodfinal.teaches_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edupodfinal.adapters.DayPlannerAdapter
import com.example.edupodfinal.databinding.FragmentRecordHomeBinding
import com.example.edupodfinal.firebase.FirestoreClass
import com.example.edupodfinal.models.DailyPlanner


class RecordHome : Fragment() {

    private var _binding: FragmentRecordHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var dailyPlanner: DayPlannerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecordHomeBinding.inflate(inflater, container, false)

        dailyPlanner = DayPlannerAdapter()

        binding.idTermRecord.setOnClickListener {
            findNavController().navigate(RecordHomeDirections.actionRecordHomeToTermRecordFragmant())
        }

        binding.btnDailyRec.setOnClickListener {
            findNavController().navigate(RecordHomeDirections.actionRecordHomeToDailyRecordFragment())
        }

        binding.btnAnualRec.setOnClickListener {
            findNavController().navigate(RecordHomeDirections.actionRecordHomeToAnualRecordFragment())
        }


        FirestoreClass().getDailyPlanner(this)

        binding.dayPlannerRecycler.apply {
            adapter = dailyPlanner
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        }

        return binding.root
    }


    fun sucssesDailyRecordView(dailyRecords: ArrayList<DailyPlanner>){

        dailyPlanner.submitList(dailyRecords)

        Toast.makeText(
            requireContext(),
            "daily record created",
            Toast.LENGTH_SHORT
        ).show()
    }



}