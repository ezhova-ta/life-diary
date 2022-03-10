package com.example.lifediary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.data.domain.MainNote
import com.example.lifediary.databinding.MainNoteListItemBinding
import com.example.lifediary.ui.common.UiConstants.MainScreen.NOTE_VIEW_ROLLED_UP_MAX_LINES

class MainNoteListAdapter(
	private val onItemLongClickListener: ListItemClickListener<MainNote>? = null
) : ListAdapter<MainNote, MainNoteListAdapter.ViewHolder>(MainNoteListItemDiffCallBack()) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
		ViewHolder.getInstance(parent)

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = getItem(position)
		holder.bind(item, onItemLongClickListener)
	}

	class MainNoteListItemViewModel(val mainNoteListItem: MainNote)

	class ViewHolder private constructor(
		private val binding: MainNoteListItemBinding
	) : RecyclerView.ViewHolder(binding.root) {
		companion object {
			fun getInstance(parent: ViewGroup): ViewHolder {
				val layoutInflater = LayoutInflater.from(parent.context)
				val binding = MainNoteListItemBinding.inflate(layoutInflater, parent, false)
				return ViewHolder(binding)
			}
		}

		fun bind(item: MainNote, onItemLongClickListener: ListItemClickListener<MainNote>?) {
			binding.viewModel = MainNoteListItemViewModel(item)
			setUpNoteView()
			setupClickListeners(item, onItemLongClickListener)
			binding.executePendingBindings()
		}

		private fun setUpNoteView() {
			binding.noteView.rolledUpMaxLines = NOTE_VIEW_ROLLED_UP_MAX_LINES
		}

		private fun setupClickListeners(
			item: MainNote,
			onItemLongClickListener: ListItemClickListener<MainNote>?
		) {
			binding.noteView.setOnLongClickListener {
				onItemLongClickListener?.onClick(item)
				true
			}
		}
	}
}

class MainNoteListItemDiffCallBack : DiffUtil.ItemCallback<MainNote>() {
	override fun areItemsTheSame(oldItem: MainNote, newItem: MainNote) =
		oldItem.text == newItem.text

	override fun areContentsTheSame(oldItem: MainNote, newItem: MainNote) =
		oldItem == newItem
}