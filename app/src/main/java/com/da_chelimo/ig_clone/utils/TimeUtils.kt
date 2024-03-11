package com.da_chelimo.ig_clone.utils

import org.joda.time.DateTime

fun Long.getDurationAgo(): String {
    val timeDiff = System.currentTimeMillis() - this

    // TODO: Fix this to use "when" branch with Joda time library
    return "6 hours ago"
}

fun Long.getCommentTime(): String {
    val timeDiff = System.currentTimeMillis() - this

    return "16 m"
}

fun Long.is3YearsAndBelow() = (DateTime.now().year - DateTime(this).year) <= 3

fun Long?.getYearsOld(): String {
    val yearsAgo = this?.apply { DateTime.now().year - DateTime(this).year } ?: 0
    val yearOrYears = if (yearsAgo <= 1) "year" else "years"

    return "$yearsAgo $yearOrYears old"
}

fun Long.getFormattedDateOfBirth(): String = DateTime(this).toString("dd MMMM YYYY")