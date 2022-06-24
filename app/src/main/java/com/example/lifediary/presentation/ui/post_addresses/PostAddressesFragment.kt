package com.example.lifediary.presentation.ui.post_addresses

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lifediary.R
import com.example.lifediary.presentation.adapters.ListItemClickListener
import com.example.lifediary.presentation.adapters.PostAddressListAdapter
import com.example.lifediary.databinding.FragmentPostAddressesBinding
import com.example.lifediary.presentation.ui.BaseFragment

class PostAddressesFragment : BaseFragment() {
    override val viewModel: PostAddressesViewModel by activityViewModels()
    private var _binding: FragmentPostAddressesBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun getInstance(): Fragment {
            return PostAddressesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostAddressesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupViews()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.onAttach()
    }

    private fun setupViews() {
        setupPostAddressListRecycler()
        setupClearPostAddressesConfirmationDialog()
        setupDeletePostAddressConfirmationDialog()
        setupPostAddressSearchView()
    }

    private fun setupPostAddressListRecycler() {
        val postAddressesAdapter = PostAddressListAdapter(
            ListItemClickListener { viewModel.onDeletePostAddressClick(it) },
            ListItemClickListener { viewModel.onEditPostAddressClick(it) },
            ListItemClickListener { viewModel.onPostAddressLongClick(it) }
        )
        binding.postAddressListView.adapter = postAddressesAdapter
        viewModel.addresses.observe(viewLifecycleOwner) { postAddresses ->
            postAddressesAdapter.submitList(postAddresses)
        }
    }

    private fun setupClearPostAddressesConfirmationDialog() {
        viewModel.showClearPostAddressesConfirmationDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow) showClearPostAddressesConfirmationDialog()
        }
    }

    private fun showClearPostAddressesConfirmationDialog() {
        showConfirmationDialog(
            messageRes = R.string.clear_post_addresses_confirmation,
            positiveButtonTextRes = R.string.clear,
            negativeButtonRes = R.string.cancel,
            onConfirmed = viewModel::onClearPostAddressesConfirmed,
            onCancelled = viewModel::onClearPostAddressesCancelled
        )
    }

    private fun setupDeletePostAddressConfirmationDialog() {
        viewModel.showDeletePostAddressConfirmationDialog.observe(viewLifecycleOwner) { addressId ->
            if(addressId != null) showDeletePostAddressConfirmationDialog(addressId)
        }
    }

    private fun showDeletePostAddressConfirmationDialog(addressId: Long) {
        showConfirmationDialog(
            messageRes = R.string.delete_post_address_confirmation,
            positiveButtonTextRes = R.string.delete,
            negativeButtonRes = R.string.cancel,
            onConfirmed = { viewModel.onDeletePostAddressConfirmed(addressId) },
            onCancelled = viewModel::onDeletePostAddressCancelled
        )
    }

    private fun setupPostAddressSearchView() {
        binding.searchPostAddressView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query ?: return false
                viewModel.onPostAddressSearchQuerySubmit(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText ?: return false
                viewModel.onPostAddressSearchQueryTextChanged(newText)
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}