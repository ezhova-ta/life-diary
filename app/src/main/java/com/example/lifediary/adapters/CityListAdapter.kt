package com.example.lifediary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.data.domain.City
import com.example.lifediary.databinding.CityListItemBinding

class CityListAdapter(private val onItemClickListener: OnCityListItemClickListener) :
        ListAdapter<City, CityListAdapter.ViewHolder>(CityListItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickListener)
    }

    class CityListItemViewModel(val city: City) {
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

        fun bind(item: City, onItemClickListener: OnCityListItemClickListener) {
            binding.viewModel = CityListItemViewModel(item)
            binding.executePendingBindings()
            binding.container.setOnClickListener {
                onItemClickListener.onClick(item)
            }
        }
    }
}

class CityListItemDiffCallBack : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: City, newItem: City) =
        oldItem == newItem
}

class OnCityListItemClickListener(val clickListener: (City) -> Unit) {
    fun onClick(city: City) = clickListener(city)
}