package com.ikeda.todolist.extensions

import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale("pt", "BR")

fun Date.format(): String {
    return SimpleDateFormat("EEE, dd MMM", locale).format(this)
}
