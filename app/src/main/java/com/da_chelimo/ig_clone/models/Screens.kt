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
        override fun getNavRoute() = "ConfirmationScreen/{number}"
    },
    DateOfBirth{
        override val baseRoute: String = "DateOfBirth"
    },
    CreateUsername{
        override val baseRoute: String = "CreateUsername"
        override fun getNavRoute(): String = "CreateUsername/{DOB}"
    },
    EnterNameScreen{
        override val baseRoute: String = "EnterName"
    },
    AddProfilePicScreen{
        override val baseRoute: String = "AddProfilePic"
    },

    Home{
        override val baseRoute: String = "HomeScreen"
    },
    Search{
        override val baseRoute: String = "SearchScreen"
    },

    Profile{
        override val baseRoute: String = "Profile"

        override fun getNavRoute(): String = "Profile/{userID}"
        override fun navigateHere(vararg stringList: String): String = "$baseRoute/${stringList.toList().first()}"
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
    open fun navigateHere(vararg stringList: String): String {
        var navigatedRoute = baseRoute

        stringList.forEach {
            navigatedRoute += "/$it"
        }
        return navigatedRoute
    }
    open fun getNavRoute(): String {
        return baseRoute
    }
}