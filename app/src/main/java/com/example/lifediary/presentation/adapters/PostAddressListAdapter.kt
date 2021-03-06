package com.example.lifediary.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.databinding.PostAddressListItemBinding
import com.example.lifediary.domain.models.PostAddress

class PostAddressListAdapter(
	private val onDeleteItemClickListener: ListItemClickListener<PostAddress>,
	private val onEditItemClickListener: ListItemClickListener<PostAddress>,
	private val onItemLongClickListener: ListItemClickListener<PostAddress>? = null
) : ListAdapter<PostAddress, PostAddressListAdapter.ViewHolder>(PostAddressListItemDiffCallBack()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  =
		ViewHolder.getInstance(parent)

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = getItem(position)
		holder.bind(item, onDeleteItemClickListener, onEditItemClickListener, onItemLongClickListener)
	}

	class PostAddressListItemViewModel(address: PostAddress) {
		val addressName = address.name
		val street = address.street
		val buildingNumber = address.buildingNumber
		val apartmentNumber = address.apartmentNumber
		val city = address.city
		val edgeRegion = address.edgeRegion
		val postcode = address.postcode
		val isAddresseeNameViewVisible = address.name.isNotBlank()
		val isStreetViewVisible = address.street.isNotBlank()
		val isBuildingNumberViewVisible = address.buildingNumber.isNotBlank()
		val isApartmentViewVisible = address.apartmentNumber.isNotBlank()
		val isCityViewVisible = address.city.isNotBlank()
		val isEdgeRegionViewVisible = address.edgeRegion.isNotBlank()
		val isPostcodeViewVisible = address.postcode.isNotBlank()
	}

	class ViewHolder private constructor(
		private val binding: PostAddressListItemBinding
	) : RecyclerView.ViewHolder(binding.root) {
		companion object {
			fun getInstance(parent: ViewGroup): ViewHolder {
				val layoutInflater = LayoutInflater.from(parent.context)
				val binding = PostAddressListItemBinding.inflate(layoutInflater, parent, false)
				return ViewHolder(binding)
			}
		}

		fun bind(
			item: PostAddress,
			onDeleteItemClickListener: ListItemClickListener<PostAddress>,
			onEditItemClickListener: ListItemClickListener<PostAddress>,
			onItemLongClickListener: ListItemClickListener<PostAddress>?
		) {
			binding.viewModel = PostAddressListItemViewModel(item)
			binding.deleteButton.setOnClickListener { onDeleteItemClickListener.onClick(item) }
			binding.editButton.setOnClickListener { onEditItemClickListener.onClick(item) }
			binding.addressContainer.setOnLongClickListener {
				onItemLongClickListener?.onClick(item)
				true
			}
			binding.executePendingBindings()
		}
	}
}

class PostAddressListItemDiffCallBack : DiffUtil.ItemCallback<PostAddress>() {
	override fun areItemsTheSame(oldItem: PostAddress, newItem: PostAddress) =
		oldItem.id == newItem.id

	override fun areContentsTheSame(oldItem: PostAddress, newItem: PostAddress) =
		oldItem == newItem
}