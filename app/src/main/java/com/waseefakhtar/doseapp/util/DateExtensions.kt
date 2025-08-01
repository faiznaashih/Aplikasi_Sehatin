package com.waseefakhtar.doseapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toFormattedDateString(): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(this)
}
