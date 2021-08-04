package com.example.lifediary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.data.domain.CityListItem
import com.example.lifediary.databinding.CityListItemBinding

class CityListAdapter(private val onItemClickListener: OnCityListItemClickListener) :
        ListAdapter<CityListItem, CityListAdapter.ViewHolder>(CityListItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickListener)
    }

    class CityListItemViewModel(val cityListItem: CityListItem) {
        // TODO Converted data if it needed
    }

    class ViewHolder private constructor(
        private val binding: CityListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun getInstance(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CityListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: CityListItem, onItemClickListener: OnCityListItemClickListener) {
            binding.viewModel = CityListItemViewModel(item)
            binding.executePendingBindings()
            binding.container.setOnClickListener {
                onItemClickListener.onClick(item)
            }
        }
    }
}

class CityListItemDiffCallBack : DiffUtil.ItemCallback<CityListItem>() {
    override fun areItemsTheSame(oldItem: CityListItem, newItem: CityListItem) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: CityListItem, newItem: CityListItem) =
        oldItem == newItem
}

class OnCityListItemClickListener(val clickListener: (CityListItem) -> Unit) {
    fun onClick(cityListItem: CityListItem) = clickListener(cityListItem)
}