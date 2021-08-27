package com.example.lifediary.ui.post_addresses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lifediary.adapters.OnPostAddressListItemClickListener
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
        return binding.root
    }

    private fun setupPostAddressListRecycler() {
        val postAddressesAdapter = PostAddressListAdapter(
            OnPostAddressListItemClickListener { viewModel.onDeletePostAddressClick(it) },
            OnPostAddressListItemClickListener { viewModel.onEditPostAddressClick(it) }
        )
        binding.postAddressListView.adapter = postAddressesAdapter
        viewModel.addresses.observe(viewLifecycleOwner) { postAddresses ->
            postAddressesAdapter.submitList(postAddresses)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}