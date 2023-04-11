package com.filimonov.mycrochet.ui.screens.details

import java.sql.Timestamp
import java.util.Calendar
import java.util.GregorianCalendar

// todo: fix 0 oldMillis
fun Timestamp.getDifferenceAgo(new: Timestamp): String {
    val oldMillis = this.time
    val newMillis = new.time

    val oldCalendar = GregorianCalendar.getInstance().apply { timeInMillis = oldMillis }
    val newCalendar = GregorianCalendar.getInstance().apply { timeInMillis = newMillis }

    val weeks = newCalendar.get(Calendar.WEEK_OF_YEAR) - oldCalendar.get(Calendar.WEEK_OF_YEAR)
    val days = newCalendar.get(Calendar.DAY_OF_YEAR) - oldCalendar.get(Calendar.DAY_OF_YEAR)
    val hours = newCalendar.get(Calendar.HOUR_OF_DAY) - oldCalendar.get(Calendar.HOUR_OF_DAY)
    val minutes = newCalendar.get(Calendar.MINUTE) - oldCalendar.get(Calendar.MINUTE)
    val seconds = newCalendar.get(Calendar.SECOND) - oldCalendar.get(Calendar.SECOND)

    return when {
        weeks > 0 -> "$weeks weeks ago"
        days > 0 -> "$days days ago"
        hours > 0 -> "$hours hours ago"
        minutes > 0 -> "$minutes minutes ago"
        seconds > 0 -> "$seconds seconds ago"
        else -> "right now"
    }
}
