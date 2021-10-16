package com.example.edupodfinal.teaches_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edupodfinal.adapters.DailyRecodAdapter
import com.example.edupodfinal.adapters.TermRecordAdapter
import com.example.edupodfinal.databinding.FragmentAddedRecordViewBinding
import com.example.edupodfinal.databinding.FragmentTermRecordViewBinding
import com.example.edupodfinal.firebase.FirestoreClass
import com.example.edupodfinal.models.DailyRecord
import com.example.edupodfinal.models.TermRecord

class TermRecordViewFragment : Fragment() {

    private var _binding: FragmentTermRecordViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var termRecordAdapter: TermRecordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTermRecordViewBinding.inflate(inflater, container, false)

        termRecordAdapter = TermRecordAdapter()

        FirestoreClass().getTermsRecList(this)


        binding.termrecordRecycler.apply {
            adapter = termRecordAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        }

        return binding.root

    }


    fun sucssestermRecord(termRecords: ArrayList<TermRecord>){

        termRecordAdapter.submitList(termRecords)

        Toast.makeText(
            requireContext(),
            "daily record created",
            Toast.LENGTH_SHORT
        ).show()
    }


}