package com.example.lifediary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.R
import com.example.lifediary.data.domain.ShoppingListItem
import com.example.lifediary.databinding.ShoppingListItemBinding

class ShoppingListAdapter(
    private val onItemClickListener: ListItemClickListener<ShoppingListItem>? = null,
    private val onHighPriorityClickListener: ListItemClickListener<ShoppingListItem>? = null,
    private val onDeleteItemClickListener: ListItemClickListener<ShoppingListItem>? = null
) : ListAdapter<ShoppingListItem, ShoppingListAdapter.ViewHolder>(ShoppingListItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(
            item,
            onItemClickListener,
            onHighPriorityClickListener,
            onDeleteItemClickListener
        )
    }

    class ShoppingListItemViewModel(val shoppingListItem: ShoppingListItem) {
        // TODO Converted data if it needed
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
            onItemClickListener: ListItemClickListener<ShoppingListItem>?,
            onHighPriorityClickListener: ListItemClickListener<ShoppingListItem>?,
            onDeleteItemClickListener: ListItemClickListener<ShoppingListItem>?
        ) {
            binding.viewModel = ShoppingListItemViewModel(item)
            binding.executePendingBindings()
            setupHighPriorityButton(item)
            setupCrossOutLine(item)

            setClickListeners(
                item,
                onItemClickListener,
                onHighPriorityClickListener,
                onDeleteItemClickListener
            )
        }

        private fun setupHighPriorityButton(item: ShoppingListItem) {
            val highPriorityButtonImageResource = if(item.isHighPriority) {
                R.drawable.icon_high_priority_red
            } else {
                R.drawable.icon_high_priority_gray
            }

            binding.setHighPriorityButton.setImageResource(highPriorityButtonImageResource)
        }

        private fun setupCrossOutLine(item: ShoppingListItem) {
            binding.crossOutLine.isVisible = item.isCrossedOut
        }

        private fun setClickListeners(
            item: ShoppingListItem,
            onItemClickListener: ListItemClickListener<ShoppingListItem>?,
            onHighPriorityClickListener: ListItemClickListener<ShoppingListItem>?,
            onDeleteItemClickListener: ListItemClickListener<ShoppingListItem>?
        ) {
            binding.titleView.setOnClickListener {
                onItemClickListener?.onClick(item)
            }

            binding.setHighPriorityButtonContainer.setOnClickListener {
                onHighPriorityClickListener?.onClick(item)
            }

            binding.deleteButtonContainer.setOnClickListener {
                onDeleteItemClickListener?.onClick(item)
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