package com.example.lifediary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.data.domain.MainNote
import com.example.lifediary.databinding.MainNoteListItemBinding
import com.example.lifediary.ui.calendar.date.CalendarDateFragment

class MainNoteListAdapter(
	private val onEditItemClickListener: OnMainNoteListItemClickListener? = null,
	private val onDeleteItemClickListener: OnMainNoteListItemClickListener? = null
) : ListAdapter<MainNote, MainNoteListAdapter.ViewHolder>(MainNoteListItemDiffCallBack()) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
		ViewHolder.getInstance(parent)

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = getItem(position)
		holder.bind(item, onEditItemClickListener, onDeleteItemClickListener)
	}

	class MainNoteListItemViewModel(val mainNoteListItem: MainNote)

	class ViewHolder private constructor(
		private val binding: MainNoteListItemBinding
	) : RecyclerView.ViewHolder(binding.root) {
		companion object {
			private const val NOTE_VIEW_ROLLED_UP_MAX_LINES = 5

			fun getInstance(parent: ViewGroup): ViewHolder {
				val layoutInflater = LayoutInflater.from(parent.context)
				val binding = MainNoteListItemBinding.inflate(layoutInflater, parent, false)
				return ViewHolder(binding)
			}
		}

		fun bind(
			item: MainNote,
			onEditItemClickListener: OnMainNoteListItemClickListener?,
			onDeleteItemClickListener: OnMainNoteListItemClickListener?
		) {
			binding.viewModel = MainNoteListItemViewModel(item)
			binding.executePendingBindings()
			setUpNoteView()
			setupClickListeners(item, onEditItemClickListener, onDeleteItemClickListener)
		}

		private fun setUpNoteView() {
			binding.noteView.rolledUpMaxLines = NOTE_VIEW_ROLLED_UP_MAX_LINES
		}

		private fun setupClickListeners(
			item: MainNote,
			onEditItemClickListener: OnMainNoteListItemClickListener?,
			onDeleteItemClickListener: OnMainNoteListItemClickListener?
		) {
			binding.editNoteButton.setOnClickListener {
				onEditItemClickListener?.onClick(item)
			}

			binding.deleteNoteButton.setOnClickListener {
				onDeleteItemClickListener?.onClick(item)
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

class OnMainNoteListItemClickListener(val clickListener: (MainNote) -> Unit) {
	fun onClick(mainNoteListItem: MainNote) = clickListener(mainNoteListItem)
}