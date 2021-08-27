package com.example.lifediary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifediary.data.domain.PostAddress
import com.example.lifediary.databinding.PostAddressListItemBinding

class PostAddressListAdapter(
	private val onDeleteItemClickListener: OnPostAddressListItemClickListener,
	private val onEditItemClickListener: OnPostAddressListItemClickListener
) : ListAdapter<PostAddress, PostAddressListAdapter.ViewHolder>(PostAddressListItemDiffCallBack()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  =
		ViewHolder.getInstance(parent)

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = getItem(position)
		holder.bind(item, onDeleteItemClickListener, onEditItemClickListener)
	}

	class PostAddressListItemViewModel(val address: PostAddress) {
		val isApartmentViewVisible = address.apartmentNumber.isNotBlank()
		val isEdgeRegionViewVisible = address.edgeRegion.isNotBlank()
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
			onDeleteItemClickListener: OnPostAddressListItemClickListener,
			onEditItemClickListener: OnPostAddressListItemClickListener
		) {
			binding.viewModel = PostAddressListItemViewModel(item)
			binding.executePendingBindings()
			binding.deleteButton.setOnClickListener { onDeleteItemClickListener.onClick(item) }
			binding.editButton.setOnClickListener { onEditItemClickListener.onClick(item) }
		}
	}
}

class PostAddressListItemDiffCallBack : DiffUtil.ItemCallback<PostAddress>() {
	override fun areItemsTheSame(oldItem: PostAddress, newItem: PostAddress) =
		oldItem.name == newItem.name

	override fun areContentsTheSame(oldItem: PostAddress, newItem: PostAddress) =
		oldItem == newItem
}

class OnPostAddressListItemClickListener(val clickListener: (PostAddress) -> Unit) {
	fun onClick(address: PostAddress) = clickListener(address)
}