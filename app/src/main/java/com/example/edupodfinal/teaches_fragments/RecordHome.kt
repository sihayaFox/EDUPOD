package com.example.edupodfinal.teaches_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.edupodfinal.R
import com.example.edupodfinal.databinding.FragmentRecordHomeBinding
import com.example.edupodfinal.databinding.FragmentTeacherReg03Binding


class RecordHome : Fragment() {

    private var _binding: FragmentRecordHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecordHomeBinding.inflate(inflater, container, false)

        binding.btnTermRecord.setOnClickListener {
            findNavController().navigate(RecordHomeDirections.actionRecordHomeToTermRecordFragmant())
        }

        return binding.root
    }

}