package com.example.lifediary.ui.shopping_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.data.domain.ShoppingListItem
import com.example.lifediary.data.domain.SortMethodDropDownItem
import com.example.lifediary.data.domain.Text
import com.example.lifediary.data.repositories.ShoppingListRepository
import com.example.lifediary.di.DiScopes
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.livedata.TwoSourceLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import toothpick.locators.FactoryLocator
import javax.inject.Inject

class ShoppingListViewModel: BaseViewModel() {
    @Inject lateinit var shoppingListRepository: ShoppingListRepository
    val shoppingList by lazy { getSortedShoppingList() }
    val isShoppingListVisible by lazy { shoppingList.map { it.isNotEmpty() } }
    val newShoppingListItemText = MutableLiveData("")
    val shoppingListSortMethodId by lazy { shoppingListRepository.getShoppingListSortMethodId() }

    private val _showClearShoppingListConfirmationDialog = MutableLiveData(false)
    val showClearShoppingListConfirmationDialog: LiveData<Boolean>
        get() = _showClearShoppingListConfirmationDialog

    init {
        bindScope()
    }

    override fun bindScope() {
        val shoppingListScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.SHOPPING_LIST_SCOPE)
        Toothpick.inject(this, shoppingListScope)
    }

    private fun getSortedShoppingList(): LiveData<List<ShoppingListItem>> {
        return TwoSourceLiveData<List<ShoppingListItem>, Int?, List<ShoppingListItem>>(
            shoppingListRepository.getShoppingList(),
            shoppingListRepository.getShoppingListSortMethodId()
        ) { originalList, sortMethodId ->
            originalList ?: return@TwoSourceLiveData emptyList<ShoppingListItem>()
            val sorter = ShoppingListSorter.Factory.getInstance(sortMethodId)
            sorter.sort(originalList)
        }
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
                shoppingListRepository.saveShoppingListItem(item)
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
        clearShoppingList()
    }

    private fun clearShoppingList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                shoppingListRepository.clearShoppingList()
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
                shoppingListRepository.inverseListItemCrossedOut(itemId)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error))
            }
        }
    }

    fun onShoppingListItemLongClick(item: ShoppingListItem) {
        copyToClipboard(item.text)
        showMessage(Text.TextResource(R.string.text_copied))
    }

    fun onHighPriorityShoppingListItemClick(item: ShoppingListItem) {
        val itemId = item.id ?: return
        CoroutineScope(Dispatchers.IO).launch {
            try {
                shoppingListRepository.inverseShoppingListItemPriority(itemId)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error))
            }
        }
    }

    fun onDeleteShoppingListItemClick(item: ShoppingListItem) {
        val itemId = item.id ?: return
        CoroutineScope(Dispatchers.IO).launch {
            try {
                shoppingListRepository.deleteShoppingListItem(itemId)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.deleting_item_error))
            }
        }
    }

    fun onSortMethodSelected(sortMethod: SortMethodDropDownItem) {
        saveShoppingListSortMethod(sortMethod)
    }

    private fun saveShoppingListSortMethod(sortMethod: SortMethodDropDownItem) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                shoppingListRepository.saveShoppingListSortMethodId(sortMethod.id)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error))
            }
        }
    }

    override fun onCleared() {
        Toothpick.closeScope(DiScopes.SHOPPING_LIST_SCOPE)
        super.onCleared()
    }
}