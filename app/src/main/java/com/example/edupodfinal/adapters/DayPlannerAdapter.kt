package com.example.edupodfinal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.edupodfinal.databinding.ItemDayplannerBinding
import com.example.edupodfinal.models.DailyPlanner

class DayPlannerAdapter : ListAdapter<DailyPlanner, DayPlannerAdapter.DayPlannerViewHolder>(
    DiffCallBack()
) {

    class DayPlannerViewHolder(val binding: ItemDayplannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DailyPlanner) {

            itemView.apply {

                val daysArray = item.date?.split(" ")

                binding.txtDateValue.text = daysArray?.get(0)
                binding.txtTime.text = item.startTime
                binding.txtSubject2.text = item.subjectName
                binding.txtEndTime2.text = item.endTime
                binding.txtGrade.text = item.className
                binding.txtDate.text = item.dayInText

            }
        }

        companion object {
            fun from(parent: ViewGroup): DayPlannerViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDayplannerBinding.inflate(layoutInflater, parent, false)
                return DayPlannerViewHolder(binding)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayPlannerViewHolder {
        return DayPlannerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DayPlannerViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    class DiffCallBack : DiffUtil.ItemCallback<DailyPlanner>() {
        override fun areItemsTheSame(oldItem: DailyPlanner, newItem: DailyPlanner) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DailyPlanner, newItem: DailyPlanner) =
            oldItem == newItem
    }


}