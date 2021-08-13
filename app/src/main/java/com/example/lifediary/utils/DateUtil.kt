package com.example.lifediary.utils

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.toDateString(): String {
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return format.format(time)
}

fun Calendar.toDateTimeString(withMilliseconds: Boolean = false): String {
    val pattern = if(withMilliseconds) "dd.MM.yyyy HH:mm:ss" else "dd.MM.yyyy HH:mm"
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(time)
}