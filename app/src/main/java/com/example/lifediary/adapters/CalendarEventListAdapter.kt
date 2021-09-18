package com.example.lifediary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.databinding.CalendarEventListItemBinding
import com.example.lifediary.utils.CalendarEvent

class CalendarEventListAdapter : ListAdapter<CalendarEvent, CalendarEventListAdapter.ViewHolder>(EventListItemDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class CalendarEventListItemViewModel(val event: CalendarEvent)

    class ViewHolder private constructor(
        private val binding: CalendarEventListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun getInstance(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CalendarEventListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: CalendarEvent) {
            binding.viewModel = CalendarEventListItemViewModel(item)
            binding.executePendingBindings()
        }
    }
}

class EventListItemDiffCallBack : DiffUtil.ItemCallback<CalendarEvent>() {
    override fun areItemsTheSame(oldItem: CalendarEvent, newItem: CalendarEvent) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: CalendarEvent, newItem: CalendarEvent) =
        oldItem == newItem
}