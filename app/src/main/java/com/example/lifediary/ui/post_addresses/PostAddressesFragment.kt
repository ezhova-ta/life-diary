package com.example.lifediary.ui.post_addresses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lifediary.R
import com.example.lifediary.adapters.ListItemClickListener
import com.example.lifediary.adapters.PostAddressListAdapter
import com.example.lifediary.databinding.FragmentPostAddressesBinding
import com.example.lifediary.ui.BaseFragment

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
        setupPostAddressListRecycler()
        setupClearPostAddressesConfirmationDialog()
        return binding.root
    }

    private fun setupPostAddressListRecycler() {
        val postAddressesAdapter = PostAddressListAdapter(
            ListItemClickListener { viewModel.onDeletePostAddressClick(it) },
            ListItemClickListener { viewModel.onEditPostAddressClick(it) }
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
        showDefaultConfirmationDialog(
            messageRes = R.string.clear_post_addresses_confirmation,
            positiveButtonTextRes = R.string.clear,
            negativeButtonRes = R.string.cancel,
            onConfirmed = viewModel::onClearPostAddressesConfirmed,
            onCancelled = viewModel::onClearPostAddressesCancelled
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}