package com.example.lifediary.presentation.models

import android.content.Context
import androidx.annotation.StringRes

sealed class Text {
	abstract fun getText(context: Context): String

	object Empty : Text() {
		override fun getText(context: Context) = ""
	}

	class TextResource(@StringRes val resId: Int) : Text() {
		override fun getText(context: Context): String =
			context.resources.getString(resId)
	}

	class TextString(val text: String) : Text() {
		override fun getText(context: Context) = text
	}
}
