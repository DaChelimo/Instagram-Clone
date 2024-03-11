package com.da_chelimo.ig_clone.utils

fun Int.toFormattedNum(): String =
    if (this < 1000) this.toString()
    else if (this < 10000) "${toString().first()},${toString().substring(1)}"
    else if (this < 1_000_000) "${this.div(1000)}.${(this.toDouble() / 1000).toString().split(".").last().first()}K"
    else if (this < 1_000_000_000) "${this.div(1_000_000)}.${(this.toDouble() / 1_000_000).toString().split(".").last().first()}M"
    else "--B"

fun Int.toFormattedNumWithSymbols(): String =
    if (this < 1000) this.toString()
    else if (this < 10000) "${toString().first()},${toString().substring(1)}"
    else if (this < 1_000_000) "${this.div(1000)}.${(this.toDouble() / 1000).toString().split(".").last().first()}K"
    else if (this < 1_000_000_000) "${this.div(1_000_000)}.${(this.toDouble() / 1_000_000).toString().split(".").last().first()}M"
    else "--B"