package com.example.lifediary.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.databinding.LocationListItemBinding
import com.example.lifediary.domain.models.Location
import com.example.lifediary.presentation.utils.getFormattedCoordinatesString

class LocationListAdapter(private val onItemClickListener: ListItemClickListener<Location>) :
        ListAdapter<Location, LocationListAdapter.ViewHolder>(LocationListItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickListener)
    }

    class LocationListItemViewModel(location: Location) {
        val locationName = location.name
        val coordinates = location.getFormattedCoordinatesString()
        val region = location.region
        val country = location.country
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

        fun bind(item: Location, onItemClickListener: ListItemClickListener<Location>) {
            binding.viewModel = LocationListItemViewModel(item)
            binding.container.setOnClickListener { onItemClickListener.onClick(item) }
            binding.executePendingBindings()
        }
    }
}

class LocationListItemDiffCallBack : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Location, newItem: Location) =
        oldItem == newItem
}