package com.example.lifediary.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.databinding.ToDoListItemBinding
import com.example.lifediary.domain.models.ToDoListItem
import com.example.lifediary.domain.utils.toTimeString

class ToDoListAdapter(
	private val onDeleteItemClickListener: ListItemClickListener<ToDoListItem>,
	private val onEnableNotificationClickListener: ListItemClickListener<ToDoListItem>,
	private val onItemClickListener: ListItemClickListener<ToDoListItem>? = null,
	private val onItemLongClickListener: ListItemClickListener<ToDoListItem>? = null,
) : ListAdapter<ToDoListItem, ToDoListAdapter.ViewHolder>(ToDoItemDiffCallBack()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		ViewHolder.getInstance(parent)

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = getItem(position)
		holder.bind(
			item,
			onDeleteItemClickListener,
			onEnableNotificationClickListener,
			onItemClickListener,
			onItemLongClickListener
		)
	}

	class ToDoListItemViewModel(toDoListItem: ToDoListItem) {
		val toDoListItemText = toDoListItem.text
		val isChecked = toDoListItem.isDone
		val notificationEnabled = toDoListItem.notificationEnabled
		val notificationTime = toDoListItem.notificationTime.toTimeString()
	}

	class ViewHolder private constructor(
		private val binding: ToDoListItemBinding
	) : RecyclerView.ViewHolder(binding.root) {

		companion object {
			fun getInstance(parent: ViewGroup): ViewHolder {
				val layoutInflater = LayoutInflater.from(parent.context)
				val binding = ToDoListItemBinding.inflate(layoutInflater, parent, false)
				return ViewHolder(binding)
			}
		}

		fun bind(
			item: ToDoListItem,
			onDeleteItemClickListener: ListItemClickListener<ToDoListItem>,
			onEnableNotificationClickListener: ListItemClickListener<ToDoListItem>,
			onItemClickListener: ListItemClickListener<ToDoListItem>?,
			onItemLongClickListener: ListItemClickListener<ToDoListItem>?,
		) = with(binding) {
			viewModel = ToDoListItemViewModel(item)
			textContainer.setOnClickListener { onItemClickListener?.onClick(item) }
			deleteButton.setOnClickListener { onDeleteItemClickListener.onClick(item) }
			notificationButton.setOnClickListener { onEnableNotificationClickListener.onClick(item) }
			textContainer.setOnLongClickListener {
				onItemLongClickListener?.onClick(item)
				true
			}
			executePendingBindings()
		}
	}
}

class ToDoItemDiffCallBack : DiffUtil.ItemCallback<ToDoListItem>() {
	override fun areItemsTheSame(oldItem: ToDoListItem, newItem: ToDoListItem) =
		oldItem.id == newItem.id

	override fun areContentsTheSame(oldItem: ToDoListItem, newItem: ToDoListItem) =
		oldItem == newItem
}