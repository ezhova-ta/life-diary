package com.example.lifediary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.data.domain.MenstruationDates
import com.example.lifediary.databinding.MenstruationDatesListItemBinding

class MenstruationDatesListAdapter(
    private val onDeleteItemClickListener: ListItemClickListener<MenstruationDates>
) : ListAdapter<MenstruationDates, MenstruationDatesListAdapter.ViewHolder>(MenstruationDatesListItemDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onDeleteItemClickListener)
    }

    class MenstruationDatesListItemViewModel(menstruationDates: MenstruationDates) {
        val outputFormattedMenstruationDates = menstruationDates.toOutputFormattedString()
    }

    class ViewHolder private constructor(
        private val binding: MenstruationDatesListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun getInstance(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    MenstruationDatesListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(
            item: MenstruationDates,
            onDeleteItemClickListener: ListItemClickListener<MenstruationDates>
        ) {
            binding.viewModel = MenstruationDatesListItemViewModel(item)
            binding.deleteButton.setOnClickListener { onDeleteItemClickListener.onClick(item) }
            binding.executePendingBindings()
        }
    }
}

class MenstruationDatesListItemDiffCallBack : DiffUtil.ItemCallback<MenstruationDates>() {
    override fun areItemsTheSame(oldItem: MenstruationDates, newItem: MenstruationDates) =
        oldItem.startDate == newItem.startDate && oldItem.endDate == newItem.endDate

    override fun areContentsTheSame(oldItem: MenstruationDates, newItem: MenstruationDates) =
        oldItem == newItem
}