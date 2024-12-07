package com.freelab.tech.shootdown.utils

import java.util.Locale

fun Int.formatSecondsToMinutesSeconds(): String {
    val minutes = this / 60
    val remainingSeconds = this % 60
    return String.format(Locale.ENGLISH, "%02d:%02d", minutes, remainingSeconds)
}