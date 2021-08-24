package com.example.lifediary.ui.post_addresses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentAddEditPostAddressBinding
import com.example.lifediary.ui.BaseFragment

class AddEditPostAddressFragment : BaseFragment() {
    override val viewModel: PostAddressesViewModel by viewModels()
    private var _binding: FragmentAddEditPostAddressBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun getInstance(): Fragment {
            return AddEditPostAddressFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditPostAddressBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}