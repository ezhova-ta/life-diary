package com.example.lifediary.ui.post_addresses

import androidx.lifecycle.MutableLiveData
import com.example.lifediary.R
import com.example.lifediary.data.domain.Addressee
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.OneTimeEvent
import com.example.lifediary.utils.Text
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class PostAddressesViewModel: BaseViewModel() {
    @Inject
    lateinit var router: Router

    val addresseeName = MutableLiveData("")
    val addresseeStreet = MutableLiveData("")
    val addresseeBuildingNumber = MutableLiveData("")
    val addresseeApartmentNumber = MutableLiveData("")
    val addresseeCity = MutableLiveData("")
    val addresseeEdgeRegion = MutableLiveData("")
    val addresseePostcode = MutableLiveData("")

    init {
        bindAppScope()
    }

    fun onAddPostAddressClick() {
        router.navigateTo(Screens.getAddEditPostAddressScreen())
    }

    fun onSavePostAddressClick() {
        val addressee = createAddressee()

        if(addressee == null) {
            val messageRes = R.string.fill_all_necessary_fields
            popupMessageEvent.postValue(OneTimeEvent(Text.TextResource(messageRes)))
            return
        }

        // TODO Save addressee
    }

    // TODO Javadoc
    private fun createAddressee(): Addressee? {
        val addresseeName = this.addresseeName.value ?: return null
        val addresseeStreet = this.addresseeStreet.value ?: return null
        val addresseeBuildingNumber = this.addresseeBuildingNumber.value ?: return null
        val addresseeApartmentNumber = this.addresseeApartmentNumber.value ?: return null
        val addresseeCity = this.addresseeCity.value ?: return null
        val addresseePostcode = this.addresseePostcode.value ?: return null
        val addresseeEdgeRegion = this.addresseeEdgeRegion.value

        if(
            addresseeName.isBlank() ||
            addresseeStreet.isBlank() ||
            addresseeBuildingNumber.isBlank() ||
            addresseeApartmentNumber.isBlank() ||
            addresseeCity.isBlank() ||
            addresseePostcode.isBlank()
        ) {
            return null
        }

        return Addressee(
            addresseeName,
            addresseeStreet,
            addresseeBuildingNumber,
            addresseeApartmentNumber,
            addresseeCity,
            addresseePostcode,
            addresseeEdgeRegion
        )
    }
}