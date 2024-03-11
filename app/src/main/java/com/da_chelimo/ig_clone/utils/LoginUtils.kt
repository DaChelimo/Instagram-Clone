package com.da_chelimo.ig_clone.utils

import androidx.core.text.isDigitsOnly

fun verifyEmail(email: String): Boolean = email.isNotBlank() && email.endsWith("@gmail.com")

fun verifyNumber(number: String): Boolean = number.isDigitsOnly() && number.length == 10

fun verifyCodeLength(code: String) = code.length == 6


fun validateUserName(userName: String) =
    if (userName.contains(regex = Regex("@/}")))
        "Enter a valid username"
    else if (userName.isInDataBase())
        "Username $userName is already taken"
    else
        null

private fun String.isInDataBase(): Boolean {
    return false
}
