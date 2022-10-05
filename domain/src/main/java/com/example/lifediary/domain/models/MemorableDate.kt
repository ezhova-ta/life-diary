package com.example.lifediary.domain.models

data class MemorableDate(
	var id: Long? = null,
	var name: String,
	var dayNumber: Int,
	var monthNumber: Int,
	var year: Int? = null
) {
	fun edit(): Editor {
		return Editor(this)
	}

	class Editor(private val memorableDate: MemorableDate) {
		fun setName(name: String): Editor {
			return apply { memorableDate.name = name }
		}

		fun setDayNumber(dayNumber: Int): Editor {
			return apply { memorableDate.dayNumber = dayNumber }
		}

		fun setMonthNumber(monthNumber: Int): Editor {
			return apply { memorableDate.monthNumber = monthNumber }
		}

		fun setYear(year: Int?): Editor {
			return apply { memorableDate.year = year }
		}

		fun apply(): MemorableDate {
			return memorableDate
		}
	}
}
