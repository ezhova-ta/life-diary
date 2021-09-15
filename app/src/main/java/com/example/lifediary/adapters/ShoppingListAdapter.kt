package com.example.lifediary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.R
import com.example.lifediary.data.domain.ShoppingListItem
import com.example.lifediary.databinding.ShoppingListItemBinding

class ShoppingListAdapter(
    private val onHighPriorityClickListener: ListItemClickListener<ShoppingListItem>,
    private val onDeleteItemClickListener: ListItemClickListener<ShoppingListItem>,
    private val onItemClickListener: ListItemClickListener<ShoppingListItem>? = null,
    private val onItemLongClickListener: ListItemClickListener<ShoppingListItem>? = null
) : ListAdapter<ShoppingListItem, ShoppingListAdapter.ViewHolder>(ShoppingListItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(
            item,
            onHighPriorityClickListener,
            onDeleteItemClickListener,
            onItemClickListener,
            onItemLongClickListener
        )
    }

    class ShoppingListItemViewModel(val shoppingListItem: ShoppingListItem) {
        val isChecked = shoppingListItem.isCrossedOut
    }

    class ViewHolder private constructor(
        private val binding: ShoppingListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun getInstance(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ShoppingListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(
            item: ShoppingListItem,
            onHighPriorityClickListener: ListItemClickListener<ShoppingListItem>,
            onDeleteItemClickListener: ListItemClickListener<ShoppingListItem>,
            onItemClickListener: ListItemClickListener<ShoppingListItem>?,
            onItemLongClickListener: ListItemClickListener<ShoppingListItem>?,
        ) {
            binding.viewModel = ShoppingListItemViewModel(item)
            setupHighPriorityButton(item)
            setClickListeners(
                item,
                onHighPriorityClickListener,
                onDeleteItemClickListener,
                onItemClickListener,
                onItemLongClickListener
            )
            binding.executePendingBindings()
        }

        private fun setupHighPriorityButton(item: ShoppingListItem) {
            val highPriorityButtonImageResource = if(item.isHighPriority) {
                R.drawable.icon_high_priority_red
            } else {
                R.drawable.icon_high_priority_gray
            }

            binding.setHighPriorityButton.setImageResource(highPriorityButtonImageResource)
        }

        private fun setClickListeners(
            item: ShoppingListItem,
            onHighPriorityClickListener: ListItemClickListener<ShoppingListItem>,
            onDeleteItemClickListener: ListItemClickListener<ShoppingListItem>,
            onItemClickListener: ListItemClickListener<ShoppingListItem>?,
            onItemLongClickListener: ListItemClickListener<ShoppingListItem>?,
        ) {
            binding.titleView.setOnClickListener { onItemClickListener?.onClick(item) }
            binding.setHighPriorityButton.setOnClickListener { onHighPriorityClickListener.onClick(item) }
            binding.deleteButton.setOnClickListener { onDeleteItemClickListener.onClick(item) }

            binding.titleView.setOnLongClickListener {
                onItemLongClickListener?.onClick(item)
                true
            }
        }
    }
}

class ShoppingListItemDiffCallBack : DiffUtil.ItemCallback<ShoppingListItem>() {
    override fun areItemsTheSame(oldItem: ShoppingListItem, newItem: ShoppingListItem) =
        oldItem.text == newItem.text

    override fun areContentsTheSame(oldItem: ShoppingListItem, newItem: ShoppingListItem) =
        oldItem == newItem
}