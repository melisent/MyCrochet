package com.filimonov.mycrochet.ui.screens.details

import kotlin.time.Duration.Companion.milliseconds

fun Long.getModifiedLabel(new: Long): String {
    val oldMillis = this.milliseconds
    val newMillis = new.milliseconds

    val difference = newMillis - oldMillis
    val seconds = difference.inWholeSeconds
    val minutes = difference.inWholeMinutes
    val hours = difference.inWholeHours
    val days = difference.inWholeDays
    val weeks = days / 7

    return if (this == 0L) {
        "never modified"
    } else {
        "modified " + when {
            weeks > 4 -> "months ago"
            weeks == 1L -> "$weeks week ago"
            weeks > 1 -> "$weeks weeks ago"
            days == 1L -> "$days day ago"
            days > 0 -> "$days days ago"
            hours == 1L -> "$hours hour ago"
            hours > 0 -> "$hours hours ago"
            minutes == 1L -> "$minutes minute ago"
            minutes > 0 -> "$minutes minutes ago"
            seconds == 1L -> "$seconds second ago"
            seconds > 0 -> "$seconds seconds ago"
            else -> "right now"
        }
    }
}
