package com.example.lifediary.ui.post_addresses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.data.domain.PostAddress
import com.example.lifediary.data.repositories.PostAddressRepository
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.OneTimeEvent
import com.example.lifediary.utils.Text
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostAddressesViewModel: BaseViewModel() {
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var repository: PostAddressRepository

    val addresses: LiveData<List<PostAddress>>
    val addresseeName = MutableLiveData("")
    val addresseeStreet = MutableLiveData("")
    val addresseeBuildingNumber = MutableLiveData("")
    val addresseeApartmentNumber = MutableLiveData("")
    val addresseeCity = MutableLiveData("")
    val addresseeEdgeRegion = MutableLiveData("")
    val addresseePostcode = MutableLiveData("")
    val isAddressListVisible: LiveData<Boolean>

    init {
        bindAppScope()
        addresses = repository.getAllAddresses()
        isAddressListVisible = addresses.map { it.isNotEmpty() }
    }

    fun onDeletePostAddressClick(address: PostAddress) {
        val addressId = address.id ?: return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.deleteAddress(addressId)
            } catch(e: Exception) {
                val messageRes = R.string.deleting_item_error
                popupMessageEvent.postValue(OneTimeEvent(Text.TextResource(messageRes)))
            }
        }
    }

    fun onAddPostAddressClick() {
        router.navigateTo(Screens.getAddEditPostAddressScreen())
    }

    fun onSavePostAddressClick() {
        val address = createAddress()

        if(address == null) {
            val messageRes = R.string.error_try_again
            popupMessageEvent.postValue(OneTimeEvent(Text.TextResource(messageRes)))
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.addAddress(address)
                router.exit()
            } catch(e: Exception) {
                val messageRes = R.string.failed_to_save
                popupMessageEvent.postValue(OneTimeEvent(Text.TextResource(messageRes)))
            }
        }
    }

    private fun createAddress(): PostAddress? {
        val addresseeName = this.addresseeName.value ?: return null
        val addresseeStreet = this.addresseeStreet.value ?: return null
        val addresseeBuildingNumber = this.addresseeBuildingNumber.value ?: return null
        val addresseeApartmentNumber = this.addresseeApartmentNumber.value ?: return null
        val addresseeCity = this.addresseeCity.value ?: return null
        val addresseePostcode = this.addresseePostcode.value ?: return null
        val addresseeEdgeRegion = this.addresseeEdgeRegion.value ?: return null

        return PostAddress(
            name = addresseeName,
            street = addresseeStreet,
            buildingNumber = addresseeBuildingNumber,
            apartmentNumber = addresseeApartmentNumber,
            city = addresseeCity,
            postcode = addresseePostcode,
            edgeRegion = addresseeEdgeRegion
        )
    }
}