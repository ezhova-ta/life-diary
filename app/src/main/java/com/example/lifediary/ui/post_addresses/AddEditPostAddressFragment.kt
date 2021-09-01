package com.example.lifediary.ui.post_addresses

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
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
        onReadContactsPermissionRequestCompleted(isGranted)
    }

    private val requestPickContact = registerForActivityResult(
        ActivityResultContracts.PickContact()
    ) { contactUri ->
        onContactPicked(contactUri)
    }

    companion object {
        const val READ_CONTACTS_PERMISSION_NAME = Manifest.permission.READ_CONTACTS

        fun getInstance(): Fragment {
            return AddEditPostAddressFragment()
        }
    }

    private fun onReadContactsPermissionRequestCompleted(isGranted: Boolean) {
        if(isGranted) {
            launchPickContactRequest()
        } else {
            viewModel.onPickContactPermissionNotGranted()
        }
    }

    private fun onContactPicked(contactUri: Uri) {
        val cursor = requireContext().contentResolver.query(
            contactUri,
            arrayOf(ContactsContract.Contacts.DISPLAY_NAME),
            null,
            null,
            null
        )

        if(cursor != null && cursor.moveToFirst()) {
            val name = cursor.getString(0)
            viewModel.onContactPicked(name)
        } else {
            viewModel.onPickContactFailed()
        }

        cursor?.close()
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
        if(isPermissionGranted(READ_CONTACTS_PERMISSION_NAME)) {
            launchPickContactRequest()
        } else {
            launchReadContactsPermissionRequest()
        }
    }

    private fun launchPickContactRequest() {
        requestPickContact.launch(null)
    }

    private fun launchReadContactsPermissionRequest() {
        requestReadContactsPermission.launch(READ_CONTACTS_PERMISSION_NAME)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}