package com.da_chelimo.ig_clone.models

enum class Screen {
    HOME,
    PROFILE
}

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(Screen.HOME.name)
    data class Profile(val userID: String) : NavigationItem("${Screen.PROFILE.name}/$userID")
}