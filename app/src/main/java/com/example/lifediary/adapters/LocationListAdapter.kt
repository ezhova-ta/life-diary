package com.example.lifediary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.data.domain.Location
import com.example.lifediary.databinding.LocationListItemBinding

class LocationListAdapter(private val onItemClickListener: OnLocationListItemClickListener) :
        ListAdapter<Location, LocationListAdapter.ViewHolder>(LocationListItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickListener)
    }

    class LocationListItemViewModel(val location: Location) {
        // TODO Converted data if it needed
    }

    class ViewHolder private constructor(
        private val binding: LocationListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun getInstance(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LocationListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: Location, onItemClickListener: OnLocationListItemClickListener) {
            binding.viewModel = LocationListItemViewModel(item)
            binding.executePendingBindings()
            binding.container.setOnClickListener {
                onItemClickListener.onClick(item)
            }
        }
    }
}

class LocationListItemDiffCallBack : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Location, newItem: Location) =
        oldItem == newItem
}

class OnLocationListItemClickListener(val clickListener: (Location) -> Unit) {
    fun onClick(location: Location) = clickListener(location)
}