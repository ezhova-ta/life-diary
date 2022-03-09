package com.example.lifediary.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lifediary.utils.OneTimeEvent
import com.example.lifediary.data.domain.Text

abstract class BaseViewModel : ViewModel() {
    private val _popupMessageEvent = MutableLiveData<OneTimeEvent<Text>>()
    val popupMessageEvent: LiveData<OneTimeEvent<Text>>
        get() = _popupMessageEvent

    private val _copyToClipboardEvent = MutableLiveData<String>()
    val copyToClipboardEvent: LiveData<String>
        get() = _copyToClipboardEvent

    abstract fun bindScope()

    protected fun showMessage(text: Text) {
        _popupMessageEvent.postValue(OneTimeEvent(text))
    }

    protected fun copyToClipboard(text: String) {
        _copyToClipboardEvent.postValue(text)
    }
}