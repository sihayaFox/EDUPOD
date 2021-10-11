package com.example.edupodfinal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.edupodfinal.databinding.ItemDailyRecordBinding
import com.example.edupodfinal.models.DailyRecord

class DailyRecodAdapter(): ListAdapter<DailyRecord, DailyRecodAdapter.DailyRecordViewHolder>(DiffCallBack()) {

     class DailyRecordViewHolder(val binding:ItemDailyRecordBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item:DailyRecord){
            itemView.apply {

                binding.txtStartTime.text = item.startTime
                binding.txtEndTime.text = item.endTime
                binding.txtClass.text = item.className
                binding.txtSubject.text = "Subject: ${item.subjectName}"
                binding.txtActivity.text = item.activityName

            }
        }

        companion object {
            fun from(parent: ViewGroup): DailyRecordViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDailyRecordBinding.inflate(layoutInflater, parent, false)
                return DailyRecordViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyRecordViewHolder {
        return  DailyRecordViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DailyRecordViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    override fun submitList(list: List<DailyRecord>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    class DiffCallBack: DiffUtil.ItemCallback<DailyRecord>(){
        override fun areItemsTheSame(oldItem: DailyRecord, newItem: DailyRecord) =
            oldItem.dailyRecId == newItem.dailyRecId

        override fun areContentsTheSame(oldItem: DailyRecord, newItem: DailyRecord) =
            oldItem == newItem
    }




}