package com.example.lifediary.domain.models

data class BackupData(
	val mainNotes: List<MainNote>,
	val dateNotes: List<DateNote>,
	val toDoLists: List<ToDoListItem>,
	val shoppingList: List<ShoppingListItem>,
	val postAddresses: List<PostAddress>,
	val memorableDates: List<MemorableDate>,
	val menstruationPeriods: List<MenstruationPeriod>
)
