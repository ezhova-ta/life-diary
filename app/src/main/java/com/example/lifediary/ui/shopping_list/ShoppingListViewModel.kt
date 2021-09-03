package com.example.lifediary.ui.shopping_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.data.domain.ShoppingListItem
import com.example.lifediary.data.repositories.ShoppingListRepository
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingListViewModel: BaseViewModel() {
    @Inject lateinit var repository: ShoppingListRepository
    var shoppingList: LiveData<List<ShoppingListItem>>
    val newShoppingListItemText = MutableLiveData("")
    val isShoppingListVisible: LiveData<Boolean>

    private val _showClearShoppingListConfirmationDialog = MutableLiveData(false)
    val showClearShoppingListConfirmationDialog: LiveData<Boolean>
        get() = _showClearShoppingListConfirmationDialog

    init {
        bindAppScope()
        shoppingList = repository.getShoppingList()
        isShoppingListVisible = shoppingList.map { it.isNotEmpty() }
    }

    fun onAddShoppingListItemClick() {
        onAddShoppingListItemInputDone()
    }

    fun onAddShoppingListItemInputDone() {
        val text = newShoppingListItemText.value?.trim()

        if(text.isNullOrBlank()) {
            newShoppingListItemText.value = ""
            return
        }

        val item = ShoppingListItem(text = text)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.saveShoppingListItem(item)
                newShoppingListItemText.postValue("")
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_save))
            }
        }
    }

    fun onClearShoppingListClick() {
        if(shoppingList.value.isNullOrEmpty()) return
        _showClearShoppingListConfirmationDialog.value = true
    }

    fun onClearShoppingListConfirmed() {
        _showClearShoppingListConfirmationDialog.value = false
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.clearShoppingList()
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_clear_list))
            }
        }
    }

    fun onClearShoppingListCancelled() {
        _showClearShoppingListConfirmationDialog.value = false
    }

    fun onShoppingListItemClick(item: ShoppingListItem) {
        val itemId = item.id ?: return
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.inverseListItemCrossedOut(itemId)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error))
            }
        }
    }

    fun onHighPriorityShoppingListItemClick(item: ShoppingListItem) {
        val itemId = item.id ?: return
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.inverseShoppingListItemPriority(itemId)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error))
            }
        }
    }

    fun onDeleteShoppingListItemClick(item: ShoppingListItem) {
        val itemId = item.id ?: return
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.deleteShoppingListItem(itemId)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.deleting_item_error))
            }
        }
    }
}