package com.da_chelimo.ig_clone.utils

import androidx.core.text.isDigitsOnly

fun verifyEmailIsValid(email: String): Boolean = email.isNotBlank() && email.endsWith("@gmail.com")
fun verifyPassword(password: String) = password.length >= 6 && !password.isDigitsOnly() // && password.contains(Regex("a-z"))
fun verifyNumberIsValid(number: String): Boolean = number.isDigitsOnly() && number.length == 10

fun verifyCodeLength(code: String) = code.length == 6


fun validateUserName(userName: String) =
    if (userName.isBlank() || userName.isEmpty())
        "Enter a valid username"
    else if (userName.isInDataBase())
        "Username $userName is already taken"
    else
        null

private fun String.isInDataBase(): Boolean {
    return false
}
