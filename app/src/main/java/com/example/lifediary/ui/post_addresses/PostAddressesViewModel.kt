package com.example.lifediary.ui.post_addresses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.data.domain.PostAddress
import com.example.lifediary.data.repositories.PostAddressRepository
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Text
import com.example.lifediary.utils.isAllItemsBlank
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostAddressesViewModel: BaseViewModel() {
    @Inject lateinit var router: Router
    @Inject lateinit var postAddressRepository: PostAddressRepository
    val addresses: LiveData<List<PostAddress>>
    val isAddressListVisible: LiveData<Boolean>

    private val _isAddButtonVisible = MutableLiveData<Boolean>()
    val isAddButtonVisible: LiveData<Boolean>
        get() = _isAddButtonVisible

    private val _showNoContactWillBeCreatedConfirmationDialog = MutableLiveData(false)
    val showNoContactWillBeCreatedConfirmationDialog: LiveData<Boolean>
        get() = _showNoContactWillBeCreatedConfirmationDialog

    private val _showContactWillBeDeletedConfirmationDialog = MutableLiveData(false)
    val showContactWillBeDeletedConfirmationDialog: LiveData<Boolean>
        get() = _showContactWillBeDeletedConfirmationDialog

    val addresseeName = MutableLiveData("")
    val addresseeStreet = MutableLiveData("")
    val addresseeBuildingNumber = MutableLiveData("")
    val addresseeApartmentNumber = MutableLiveData("")
    val addresseeCity = MutableLiveData("")
    val addresseeEdgeRegion = MutableLiveData("")
    val addresseePostcode = MutableLiveData("")

    // TODO Good solution?
    private var editingAddress: PostAddress? = null
        set(value) {
            field = value
            _isAddButtonVisible.value = value == null
            addresseeName.value = value?.name ?: ""
            addresseeStreet.value = value?.street ?: ""
            addresseeBuildingNumber.value = value?.buildingNumber ?: ""
            addresseeApartmentNumber.value = value?.apartmentNumber ?: ""
            addresseeCity.value = value?.city ?: ""
            addresseeEdgeRegion.value = value?.edgeRegion ?: ""
            addresseePostcode.value = value?.postcode ?: ""
        }

    init {
        bindAppScope()
        addresses = postAddressRepository.getAllAddresses()
        isAddressListVisible = addresses.map { it.isNotEmpty() }
    }

    fun onDeletePostAddressClick(address: PostAddress) {
        val addressId = address.id ?: return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                postAddressRepository.deleteAddress(addressId)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.deleting_item_error))
            }
        }
    }

    fun onEditPostAddressClick(address: PostAddress) {
        editingAddress = address
        router.navigateTo(Screens.getAddEditPostAddressScreen())
    }

    fun onAddPostAddressClick() {
        editingAddress = null
        router.navigateTo(Screens.getAddEditPostAddressScreen())
    }

    fun onContactPicked(contactName: String) {
        addresseeName.value = contactName
    }

    fun onPickContactFailed() {
        showMessage(Text.TextResource(R.string.failed_to_get_contact))
    }

    fun onPickContactPermissionNotGranted() {
        showMessage(Text.TextResource(R.string.requires_permission_to_access_contacts))
    }

    fun onSavePostAddressClick() {
        val address = createAddress()

        if(address == null) {
            showMessage(Text.TextResource(R.string.error_try_again_later))
            return
        }

        if(address.isEmpty()) {
            showEmptyAddressDialog()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                saveAddress(address)
                router.exit()
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_save))
            }
        }
    }

    private fun createAddress(): PostAddress? {
        val addressId = editingAddress?.id
        val addresseeName = this.addresseeName.value ?: return null
        val addresseeStreet = this.addresseeStreet.value ?: return null
        val addresseeBuildingNumber = this.addresseeBuildingNumber.value ?: return null
        val addresseeApartmentNumber = this.addresseeApartmentNumber.value ?: return null
        val addresseeCity = this.addresseeCity.value ?: return null
        val addresseePostcode = this.addresseePostcode.value ?: return null
        val addresseeEdgeRegion = this.addresseeEdgeRegion.value ?: return null

        return PostAddress(
            id = addressId,
            name = addresseeName,
            street = addresseeStreet,
            buildingNumber = addresseeBuildingNumber,
            apartmentNumber = addresseeApartmentNumber,
            city = addresseeCity,
            postcode = addresseePostcode,
            edgeRegion = addresseeEdgeRegion
        )
    }

    private fun PostAddress.isEmpty(): Boolean {
        return listOf(
            name,
            street,
            buildingNumber,
            apartmentNumber,
            city,
            postcode,
            edgeRegion
        ).isAllItemsBlank()
    }

    private fun showEmptyAddressDialog() {
        if(isAddingAddressOperation()) {
            _showNoContactWillBeCreatedConfirmationDialog.value = true
        } else {
            _showContactWillBeDeletedConfirmationDialog.value = true
        }
    }

    private suspend fun saveAddress(address: PostAddress) {
        if(isAddingAddressOperation()) {
            postAddressRepository.addAddress(address)
        } else {
            postAddressRepository.updateAddress(address)
        }
    }

    private fun isAddingAddressOperation(): Boolean {
        return editingAddress == null
    }

    fun onDeletingEmptyAddressConfirmed() {
        _showContactWillBeDeletedConfirmationDialog.value = false
        val editingAddressId = editingAddress?.id ?: return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                postAddressRepository.deleteAddress(editingAddressId)
                router.exit()
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error_try_again_later))
            }
        }
    }

    fun onCreatingEmptyContactConfirmed() {
        _showNoContactWillBeCreatedConfirmationDialog.value = false
        router.exit()
    }

    fun onDeletingEmptyAddressDialogCancelled() {
        _showContactWillBeDeletedConfirmationDialog.value = false
    }

    fun onCreatingEmptyContactDialogCancelled() {
        _showNoContactWillBeCreatedConfirmationDialog.value = false
    }
}