package com.example.lifediary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.data.domain.MenstruationPeriod
import com.example.lifediary.databinding.MenstruationPeriodListItemBinding

class MenstruationPeriodListAdapter(
    private val onDeleteItemClickListener: ListItemClickListener<MenstruationPeriod>
) : ListAdapter<MenstruationPeriod, MenstruationPeriodListAdapter.ViewHolder>(MenstruationPeriodListItemDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onDeleteItemClickListener)
    }

    class MenstruationPeriodListItemViewModel(menstruationPeriod: MenstruationPeriod) {
        val outputFormattedMenstruationPeriod = menstruationPeriod.toOutputFormattedString()
    }

    class ViewHolder private constructor(
        private val binding: MenstruationPeriodListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun getInstance(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    MenstruationPeriodListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(
            item: MenstruationPeriod,
            onDeleteItemClickListener: ListItemClickListener<MenstruationPeriod>
        ) {
            binding.viewModel = MenstruationPeriodListItemViewModel(item)
            binding.deleteButton.setOnClickListener { onDeleteItemClickListener.onClick(item) }
            binding.executePendingBindings()
        }
    }
}

class MenstruationPeriodListItemDiffCallBack : DiffUtil.ItemCallback<MenstruationPeriod>() {
    override fun areItemsTheSame(oldItem: MenstruationPeriod, newItem: MenstruationPeriod) =
        oldItem.startDate == newItem.startDate && oldItem.endDate == newItem.endDate

    override fun areContentsTheSame(oldItem: MenstruationPeriod, newItem: MenstruationPeriod) =
        oldItem == newItem
}