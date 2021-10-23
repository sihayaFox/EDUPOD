package com.example.edupodfinal.student_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.edupodfinal.R
import com.example.edupodfinal.databinding.FragmentDailyRecordBinding
import com.example.edupodfinal.databinding.StudentFragmentHomeBinding

class StudentHomeFragment : Fragment() {

    private var _binding: StudentFragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = StudentFragmentHomeBinding.inflate(inflater, container, false)

        binding.btnHelpDesk.setOnClickListener {
            findNavController().navigate(StudentHomeFragmentDirections.actionStudentHomeFragmentToStudentQuesionFragment())
        }

        return binding.root
    }

}