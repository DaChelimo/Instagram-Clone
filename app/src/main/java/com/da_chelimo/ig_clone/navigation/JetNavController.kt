package com.da_chelimo.ig_clone.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

object Screens {

    const val HOME = "home"
    const val SEARCH = "search"

    const val PROFILE = "profile"
    const val PROFILE_ID_KEY = "profile_id"

    const val SIGN_IN = "sign_in"

    const val CREATE_ACCOUNT_WITH_EMAIL = "create_account_with_email"
    const val CREATE_ACCOUNT_WITH_NUMBER = "create_account_with_number"

    const val CONFIRMATION_CODE = "confirmation_screen"
    const val CONFIRMATION_NUMBER_KEY = "number"

    const val DATE_OF_BIRTH = "date_of_birth"

    const val CREATE_USERNAME = "create_username"
    const val CREATE_USERNAME_DOB_KEY = "DOB"

    const val ENTER_NAME = "enter_name"
    const val ADD_PROFILE_PIC = "add_profile_pic"

    const val REQUEST_FOR_POST = "request_for_post"
    const val CREATE_POST = "create_post"
    const val CREATE_POST_IMAGE_URI = "image_uri"
}

@Composable
fun rememberJetNavController(navController: NavHostController = rememberNavController()): JetNavController =
    remember {
        JetNavController(navController)
    }

class JetNavController(
    val navController: NavHostController
) {

    val currentRoute = navController.currentDestination?.route

    /**
     * Returns to the previous screen
     */
    fun upPress() {
        navController.popBackStack()
    }

    /**
     * Navigates to another bottom bar screen
     */
    fun navigateToBottomBarRoute(mainDest: String) {
        if (mainDest != currentRoute) {
            navController.navigate(mainDest) {
                launchSingleTop = true
                restoreState = true

                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
            }
        }
    }


    fun navigateToCreateAccountWithEmail() {
        navigateSafely {
            navController.navigate(Screens.CREATE_ACCOUNT_WITH_EMAIL)
        }
    }

    fun navigateToCreateAccountWithNumber() {
        navigateSafely {
            navController.navigate(Screens.CREATE_ACCOUNT_WITH_NUMBER)
        }
    }

    fun navigateToConfirmationCode(phoneNumber: String) {
        navigateSafely {
            navController.navigate("${Screens.CONFIRMATION_CODE}/$phoneNumber")
        }
    }

    fun navigateToDateOfBirth() {
        navigateSafely {
            navController.navigate(Screens.DATE_OF_BIRTH)
        }
    }

    fun navigateToCreateUsername(dateOfBirth: Long) {
        navigateSafely {
            navController.navigate("${Screens.CREATE_USERNAME}/$dateOfBirth")
        }
    }

    fun navigateToEnterName() {
        navigateSafely {
            navController.navigate(Screens.ENTER_NAME)
        }
    }

    fun navigateToAddProfilePic() {
        navigateSafely {
            navController.navigate(Screens.ADD_PROFILE_PIC)
        }
    }

    fun navigateToRequestForPost() {
        navigateSafely {
            navController.navigate(Screens.REQUEST_FOR_POST)
        }
    }

    fun navigateToCreatePost(encodedImageUri: String) {
        navigateSafely {
            navController.navigate("${Screens.CREATE_POST}/$encodedImageUri")
        }
    }

    /**
     * Learned this from JetSnack....
     *
     * This prevents duplication of navigation events
     */
    fun NavBackStackEntry?.isLifecycleResumed() =
        this?.lifecycle?.currentState == Lifecycle.State.RESUMED

    private fun navigateSafely(doNavigate: () -> Unit) {
        if (navController.currentBackStackEntry.isLifecycleResumed()) {
            doNavigate()
        }
    }

}