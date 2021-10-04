package com.example.edupodfinal.teaches_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.edupodfinal.R
import com.example.edupodfinal.databinding.FragmentTeacherHomeBinding
import com.example.edupodfinal.databinding.FragmentTermRecordFragmantBinding


class TeacherHomeFragment : Fragment() {

    private var _binding: FragmentTeacherHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeacherHomeBinding.inflate(inflater, container, false)

        binding.btnRecord.setOnClickListener {
            findNavController().navigate(TeacherHomeFragmentDirections.actionTeacherHomeFragmentToRecordHome())
        }

        return binding.root

    }

}