package com.tech.core.utils.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DATE_FORMAT_PATTER = "dd/MM/yyyy"

object DateFormatter {
    fun format(timestamp: Long): String {
        val sdf = SimpleDateFormat(DATE_FORMAT_PATTER, Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}