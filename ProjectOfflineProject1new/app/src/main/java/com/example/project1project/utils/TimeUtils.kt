package com.example.project1project.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import java.util.Calendar

/*
 * TimeUtils
 * ----------
 * Converts timestamp (Long) into readable time
 * Example: 1691234567890 -> 10:42 AM
 */

object TimeUtils{
    private val timeFormatter =
        SimpleDateFormat("hh:mm a",Locale.getDefault())

    //Formatter for date headers
    private val dateFormatter =
        SimpleDateFormat("dd MMM yyyy",Locale.getDefault())
    fun formatTime(timestamp:Long):String{
        return timeFormatter.format(Date(timestamp))
    }

    //check if timestamp is today
    fun isToday(timestamp:Long):Boolean{
        val today=Calendar.getInstance()
        val date =Calendar.getInstance().apply{
            timeInMillis = timestamp
        }
        return today.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)
    }

    //check if timestamp is yesterday
    fun isYesterday(timestamp:Long):Boolean{
        val yesterday = Calendar.getInstance().apply{
            add(Calendar.DAY_OF_YEAR,-1)
        }
        val date = Calendar.getInstance().apply {
            timeInMillis = timestamp
        }
        return yesterday.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                yesterday.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)

    }
    //Format date header (TODAY / YESTERDAY / OR ANY DATE)
    fun formatDateHeader(timestamp:Long):String{
        return when{
            isToday(timestamp) -> "Today"
            isYesterday(timestamp) -> "Yesterday"
            else -> dateFormatter.format(Date(timestamp))
        }
    }
}
