package com.example.lifediary.ui.post_addresses

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lifediary.databinding.FragmentAddEditPostAddressBinding
import com.example.lifediary.ui.BaseFragment

class AddEditPostAddressFragment : BaseFragment() {
    override val viewModel: PostAddressesViewModel by activityViewModels()
    private var _binding: FragmentAddEditPostAddressBinding? = null
    private val binding get() = _binding!!

    private val requestReadContactsPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.onPickContactClick(isGranted)
    }

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
        setUpPickContactButton()
        return binding.root
    }

    private fun setUpPickContactButton() {
        binding.pickContactButton.setOnClickListener { onPickContactClick() }
    }

    private fun onPickContactClick() {
        val permission = Manifest.permission.READ_CONTACTS

        if(isPermissionGranted(permission)) {
            viewModel.onPickContactClick(true)
        } else {
            requestReadContactsPermission.launch(permission)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}