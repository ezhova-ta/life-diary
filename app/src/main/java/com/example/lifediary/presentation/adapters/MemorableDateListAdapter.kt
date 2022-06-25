package com.example.lifediary.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.databinding.MemorableDateListItemBinding
import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.domain.utils.howManyYearsAgo
import com.example.lifediary.domain.utils.isToday
import com.example.lifediary.presentation.utils.getDateString

class MemorableDateListAdapter(
	private val onEditItemClickListener: ListItemClickListener<MemorableDate>,
	private val onDeleteItemClickListener: ListItemClickListener<MemorableDate>,
	private val onItemLongClickListener: ListItemClickListener<MemorableDate>? = null
) : ListAdapter<MemorableDate, MemorableDateListAdapter.ViewHolder>(MemorableDateListItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onEditItemClickListener, onDeleteItemClickListener, onItemLongClickListener)
    }

    class MemorableDateListItemViewModel(memorableDateListItem: MemorableDate) {
        val memorableDateName = memorableDateListItem.name
        val dateString = memorableDateListItem.run { getDateString(dayNumber, monthNumber, year) }
        val yearsAgo = memorableDateListItem.howManyYearsAgo()
        val isTodayTitleVisible = memorableDateListItem.isToday()
        val isHowManyYearsAgoViewVisible = isTodayTitleVisible && yearsAgo != null
    }

    class ViewHolder private constructor(
        private val binding: MemorableDateListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun getInstance(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MemorableDateListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(
			item: MemorableDate,
			onEditItemClickListener: ListItemClickListener<MemorableDate>,
			onDeleteItemClickListener: ListItemClickListener<MemorableDate>,
			onItemLongClickListener: ListItemClickListener<MemorableDate>?
        ) {
            binding.viewModel = MemorableDateListItemViewModel(item)
            setupClickListeners(
                item,
                onEditItemClickListener,
                onDeleteItemClickListener,
                onItemLongClickListener
            )
            binding.executePendingBindings()
        }

        private fun setupClickListeners(
			item: MemorableDate,
			onEditItemClickListener: ListItemClickListener<MemorableDate>,
			onDeleteItemClickListener: ListItemClickListener<MemorableDate>,
			onItemLongClickListener: ListItemClickListener<MemorableDate>?
        ) {
            binding.editNameButton.setOnClickListener {
                onEditItemClickListener.onClick(item)
            }
            binding.deleteNameButton.setOnClickListener {
                onDeleteItemClickListener.onClick(item)
            }
            binding.nameView.setOnClickListener {
                onItemLongClickListener?.onClick(item)
            }
        }
    }
}

class MemorableDateListItemDiffCallBack : DiffUtil.ItemCallback<MemorableDate>() {
    override fun areItemsTheSame(oldItem: MemorableDate, newItem: MemorableDate) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MemorableDate, newItem: MemorableDate) =
        oldItem == newItem
}