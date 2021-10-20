package com.example.edupodfinal.teaches_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edupodfinal.databinding.FragmentAnualRecordBinding
import com.example.edupodfinal.models.AnualRecord


class AnualRecordViewFragment : Fragment() {

    private var _binding: FragmentAnualRecordBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAnualRecordBinding.inflate(inflater, container, false)





        return binding.root
    }


    fun sucssesAnnualRecordView(dailyRecords: ArrayList<AnualRecord>){

//        dailyRecAdapter.submitList(dailyRecords)

    }

}