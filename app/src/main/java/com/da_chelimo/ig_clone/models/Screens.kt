package com.da_chelimo.ig_clone.models

enum class Screen {
    HOME,
    PROFILE,
    CREATE_ACCOUNT_WITH_EMAIL,
    CREATE_ACCOUNT_WITH_NUMBER,
    CONFIRMATION_CODE
}

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(Screen.HOME.name)
    data class Profile(val userID: String) : NavigationItem("${Screen.PROFILE.name}/$userID")

    data object CreateAccountWithEmail: NavigationItem(Screen.CREATE_ACCOUNT_WITH_EMAIL.name)
    data object CreateAccountWithNumber: NavigationItem(Screen.CREATE_ACCOUNT_WITH_NUMBER.name)
    data object ConfirmationCode: NavigationItem(Screen.CONFIRMATION_CODE.name)
}


enum class Screens {

    ConfirmationCode{
        override val baseRoute: String = "ConfirmationScreen"
        override fun getNavRoute() = "ConfirmationScreen/{email}/{signInOptions}"
    },

    Home{
        override val baseRoute: String = "HomeScreen"
    },

    Profile{
        override val baseRoute: String = "Profile"

        override fun getNavRoute(): String = "Profile/{userID}"
        override fun navigateHere(vararg any: Any): String = "$baseRoute/${any.toList().first()}"
    },

    SignInScreen{
        override val baseRoute: String = "SignInScreen"
    },

    CreateAccountWithEmail{
        override val baseRoute: String = "CreateAccountWithEmail"
    },

    CreateAccountWithNumber{
        override val baseRoute: String = "CreateAccountWithNumber"
    };




    open val baseRoute = ""
    open fun navigateHere(vararg any: Any): String {
        var navigatedRoute = if (any.isEmpty()) baseRoute else "$baseRoute/"
        any.forEach {
            navigatedRoute += "/$it"
        }
        return navigatedRoute
    }
    open fun getNavRoute(): String {
        return baseRoute
    }
}