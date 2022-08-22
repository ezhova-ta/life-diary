package com.example.lifediary.presentation.ui.shopping_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.di.DiScopes
import com.example.lifediary.di.DiScopes.APP_SCOPE
import com.example.lifediary.di.DiScopes.MAIN_ACTIVITY_VIEW_MODEL_SCOPE
import com.example.lifediary.di.DiScopes.SHOPPING_LIST_VIEW_MODEL_SCOPE
import com.example.lifediary.domain.utils.sorters.ShoppingListSortMethod
import com.example.lifediary.domain.models.ShoppingListItem
import com.example.lifediary.domain.usecases.shopping_list.*
import com.example.lifediary.presentation.models.Text
import com.example.lifediary.presentation.models.dropdowns.ShoppingListDropDownItemSortMethodMapper.toSortMethod
import com.example.lifediary.presentation.models.dropdowns.ShoppingListSortMethodDropDownItem
import com.example.lifediary.presentation.ui.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class ShoppingListViewModel: BaseViewModel() {
    @Inject lateinit var getShoppingListSortMethodIdUseCase: GetShoppingListSortMethodIdUseCase
    @Inject lateinit var getSortedShoppingListUseCase: GetSortedShoppingListUseCase
    @Inject lateinit var addShoppingListItemUseCase: AddShoppingListItemUseCase
    @Inject lateinit var clearShoppingListUseCase: ClearShoppingListUseCase
    @Inject lateinit var inverseShoppingListItemCrossedOutByIdUseCase: InverseShoppingListItemCrossedOutByIdUseCase
    @Inject lateinit var inverseShoppingListItemPriorityByIdUseCase: InverseShoppingListItemPriorityByIdUseCase
    @Inject lateinit var deleteShoppingListItemByIdUseCase: DeleteShoppingListItemByIdUseCase
    @Inject lateinit var saveShoppingListSortMethodIdUseCase: SaveShoppingListSortMethodIdUseCase

    val shoppingList by lazy { getSortedShoppingListUseCase().asLiveData() }
    val isShoppingListVisible by lazy { shoppingList.map { it.isNotEmpty() } }
    val newShoppingListItemText = MutableLiveData("")
    val shoppingListSortMethodId by lazy { getShoppingListSortMethodIdUseCase().asLiveData() }
    val isShoppingListSortMethodDropDownVisible by lazy { shoppingList.map { it.isNotEmpty() } }

    private val _showClearShoppingListConfirmationDialog = MutableLiveData(false)
    val showClearShoppingListConfirmationDialog: LiveData<Boolean>
        get() = _showClearShoppingListConfirmationDialog

    init {
        bindScope()
    }

    override fun bindScope() {
        val shoppingListScope = Toothpick.openScopes(
            APP_SCOPE,
            MAIN_ACTIVITY_VIEW_MODEL_SCOPE,
            SHOPPING_LIST_VIEW_MODEL_SCOPE
        )
        Toothpick.inject(this, shoppingListScope)
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
                addShoppingListItemUseCase(item)
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
                clearShoppingListUseCase()
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
                inverseShoppingListItemCrossedOutByIdUseCase(itemId)
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
                inverseShoppingListItemPriorityByIdUseCase(itemId)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error))
            }
        }
    }

    fun onDeleteShoppingListItemClick(item: ShoppingListItem) {
        val itemId = item.id ?: return
        CoroutineScope(Dispatchers.IO).launch {
            try {
                deleteShoppingListItemByIdUseCase(itemId)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.deleting_item_error))
            }
        }
    }

    fun onSortMethodSelected(dropDownItem: ShoppingListSortMethodDropDownItem) {
        val sortMethod = dropDownItem.toSortMethod()
        saveShoppingListSortMethod(sortMethod)
    }

    private fun saveShoppingListSortMethod(sortMethod: ShoppingListSortMethod) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                saveShoppingListSortMethodIdUseCase(sortMethod.id)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error))
            }
        }
    }

    override fun onCleared() {
        Toothpick.closeScope(SHOPPING_LIST_VIEW_MODEL_SCOPE)
        super.onCleared()
    }
}