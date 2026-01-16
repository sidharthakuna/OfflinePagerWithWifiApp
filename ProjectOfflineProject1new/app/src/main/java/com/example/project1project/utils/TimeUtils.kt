package com.example.project1project.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/*
 * TimeUtils
 * ----------
 * Converts timestamp (Long) into readable time
 * Example: 1691234567890 -> 10:42 AM
 */

object TimeUtils{
    private val timeFormatter =
        SimpleDateFormat("hh:mm a",Locale.getDefault())
    fun formatTime(timestamp:Long):String{
        return timeFormatter.format(Date(timestamp))
    }
}
