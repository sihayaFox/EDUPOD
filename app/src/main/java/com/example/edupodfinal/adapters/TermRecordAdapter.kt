package com.example.edupodfinal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.edupodfinal.databinding.ItemTermRecordBinding
import com.example.edupodfinal.models.TermRecord

class TermRecordAdapter : ListAdapter<TermRecord, TermRecordAdapter.TermRecordViewHolder>(DiffCallBack()){

    class TermRecordViewHolder(val binding: ItemTermRecordBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item:TermRecord){
            itemView.apply {
               binding.txtCompetency.text = item.competency
                binding.txtCompLvl.text = item.competencyLevel
                binding.txtEndDate.text =item.targetDate
                binding.txtCompleteDate.text = item.completedDate
                binding.txtObjective.text = item.objectives
                binding.txtActivities.text = item.activities
                binding.txtQualityInput.text = item.qualityInputs
            }
        }

        companion object {
            fun from(parent: ViewGroup): TermRecordViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTermRecordBinding.inflate(layoutInflater, parent, false)
                return TermRecordViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermRecordViewHolder {
        return  TermRecordViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TermRecordViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class DiffCallBack: DiffUtil.ItemCallback<TermRecord>(){
        override fun areItemsTheSame(oldItem: TermRecord, newItem: TermRecord) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TermRecord, newItem: TermRecord) =
            oldItem == newItem
    }


}