package com.example.lifediary.ui.shopping_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifediary.data.domain.ShoppingListItem
import com.example.lifediary.data.repositories.ShoppingListRepository
import com.example.lifediary.ui.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingListViewModel: BaseViewModel() {
    @Inject
    lateinit var repository: ShoppingListRepository

    var shoppingList: LiveData<List<ShoppingListItem>>
    val showClearShoppingListConfirmationDialog = MutableLiveData(false)
    val newShoppingListItemText = MutableLiveData("")

    init {
        bindAppScope()
        shoppingList = repository.getShoppingList()
    }

    fun onAddShoppingListItemClick() {
        val text = newShoppingListItemText.value ?: return
        val item = ShoppingListItem(text = text)

        CoroutineScope(Dispatchers.IO).launch {
            repository.saveShoppingListItem(item)
            newShoppingListItemText.postValue("")
        }
    }

    fun onClearShoppingListClick() {
        if(shoppingList.value.isNullOrEmpty()) return
        showClearShoppingListConfirmationDialog.value = true
    }

    fun onClearShoppingListConfirmed() {
        showClearShoppingListConfirmationDialog.value = false
        CoroutineScope(Dispatchers.IO).launch {
            repository.clearShoppingList()
        }
    }

    fun onClearShoppingListCancelled() {
        showClearShoppingListConfirmationDialog.value = false
    }

    fun onShoppingListItemClick(item: ShoppingListItem) {
        val itemId = item.id ?: return
        CoroutineScope(Dispatchers.IO).launch {
            repository.inverseListItemCrossedOut(itemId)
        }
    }

    fun onHighPriorityShoppingListItemClick(item: ShoppingListItem) {
        val itemId = item.id ?: return
        CoroutineScope(Dispatchers.IO).launch {
            repository.inverseShoppingListItemPriority(itemId)
        }
    }

    fun onDeleteShoppingListItemClick(item: ShoppingListItem) {
        val itemId = item.id ?: return
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteShoppingListItem(itemId)
        }
    }
}