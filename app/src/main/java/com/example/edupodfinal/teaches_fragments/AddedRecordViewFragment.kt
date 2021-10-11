package com.example.edupodfinal.teaches_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edupodfinal.R
import com.example.edupodfinal.adapters.DailyRecodAdapter
import com.example.edupodfinal.databinding.FragmentAddedRecordViewBinding
import com.example.edupodfinal.databinding.FragmentDailyRecordBinding
import com.example.edupodfinal.firebase.FirestoreClass
import com.example.edupodfinal.models.DailyRecord


class AddedRecordViewFragment : Fragment() {

    private var _binding: FragmentAddedRecordViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var dailyRecAdapter:DailyRecodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentAddedRecordViewBinding.inflate(inflater, container, false)

        dailyRecAdapter = DailyRecodAdapter()

        FirestoreClass().getDailyRecList(this)

        binding.dailyRecRecycler.apply {
            adapter = dailyRecAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }

        return binding.root

    }

    fun sucssesDailyRecordView(dailyRecords: ArrayList<DailyRecord>){

        dailyRecAdapter.submitList(dailyRecords)

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