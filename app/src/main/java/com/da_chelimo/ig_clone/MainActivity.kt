package com.da_chelimo.ig_clone

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.da_chelimo.ig_clone.navigation.Screens
import com.da_chelimo.ig_clone.navigation.rememberJetNavController
import com.da_chelimo.ig_clone.ui.screens.account.create_account.AddProfilePicScreen
import com.da_chelimo.ig_clone.ui.screens.account.create_account.ConfirmationCodeScreen
import com.da_chelimo.ig_clone.ui.screens.account.create_account.DateOfBirthScreen
import com.da_chelimo.ig_clone.ui.screens.account.create_account.create_username.CreateUsernameScreen
import com.da_chelimo.ig_clone.ui.screens.account.create_account.enter_name.EnterNameScreen
import com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account.CreateAccountWithEmail
import com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account.CreateAccountWithNumber
import com.da_chelimo.ig_clone.ui.screens.account.sign_in.SignInScreen
import com.da_chelimo.ig_clone.ui.screens.home.HomeScreen
import com.da_chelimo.ig_clone.ui.screens.post.CreatePostScreen
import com.da_chelimo.ig_clone.ui.screens.post.RequestForPostScreen
import com.da_chelimo.ig_clone.ui.screens.profile.ProfileScreen
import com.da_chelimo.ig_clone.ui.screens.search.SearchScreen
import com.da_chelimo.ig_clone.ui.theme.IGCloneTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
//        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            IGCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val jetNavController = rememberJetNavController()
                    val coroutineScope = rememberCoroutineScope()

                    NavHost(
                        navController = jetNavController.navController,
                        startDestination = if (Firebase.auth.currentUser != null) Screens.HOME else Screens.SIGN_IN
                    ) {
                        composable(Screens.HOME) {
                            HomeScreen(jetNavController, coroutineScope)
                        }
                        composable(Screens.SEARCH) {
                            SearchScreen(jetNavController)
                        }

                        composable(Screens.SIGN_IN) {
                            SignInScreen(jetNavController, coroutineScope)
                        }

                        composable(Screens.CREATE_ACCOUNT_WITH_EMAIL) {
                            CreateAccountWithEmail(jetNavController, coroutineScope)
                        }

                        composable(Screens.CREATE_ACCOUNT_WITH_NUMBER) {
                            CreateAccountWithNumber(jetNavController)
                        }

                        composable(Screens.DATE_OF_BIRTH) {
                            DateOfBirthScreen(jetNavController)
                        }
                        composable(
                            route = Screens.CREATE_USERNAME,
                            arguments = listOf(
                                navArgument(Screens.CREATE_USERNAME_DOB_KEY) {
                                    type = NavType.LongType
                                }
                            )
                        ) { backStackEntry ->
                            val dateOfBirth =
                                requireNotNull(backStackEntry.arguments?.getLong("DOB"))
                            CreateUsernameScreen(jetNavController, dateOfBirth)
                        }

                        composable(Screens.ENTER_NAME) {
                            EnterNameScreen(jetNavController)
                        }
                        composable(Screens.ADD_PROFILE_PIC) {
                            AddProfilePicScreen(jetNavController)
                        }

                        composable(Screens.REQUEST_FOR_POST) {
                            RequestForPostScreen(
                                onImageSelected = { selectedImage ->
                                    val encodedUri =
                                        Uri.encode(
                                            selectedImage.toString(),
                                            StandardCharsets.UTF_8.toString()
                                        )
                                    jetNavController.navigateToCreatePost(encodedUri)
                                },
                                onImageRequestFailed = { jetNavController.upPress() }
                            )
                        }


                        // Composable with Arguments
                        composable("${Screens.CREATE_POST}/{${Screens.CREATE_POST_IMAGE_URI}}") { navBackStackEntry ->
                            val imageURI = navBackStackEntry.arguments?.getString("imageURI")!!
                            CreatePostScreen(
                                image = Uri.parse(imageURI),
                                jetNavController = jetNavController
                            )
                        }

                        /**
                         * If the argument userID is null, it means we are navigating to the current user's profile
                         * If argument userID has value, we are viewing another person's account
                         */
                        composable("${Screens.PROFILE}?${Screens.PROFILE_ID_KEY}={${Screens.PROFILE_ID_KEY}}") { navBackStackEntry ->
                            val userID = requireNotNull(
                                navBackStackEntry.arguments?.getString("userID")
                                    ?: Firebase.auth.uid
                            )
                            ProfileScreen(userID = userID, jetNavController = jetNavController)
                        }

                        composable("${Screens.CONFIRMATION_CODE}/${Screens.CONFIRMATION_NUMBER_KEY}") { backStackEntry ->
                            val number = backStackEntry.arguments?.getString("number")!!

                            ConfirmationCodeScreen(
                                number = number,
                                coroutineScope = coroutineScope,
                                jetNavController = jetNavController
                            )
                        }
                    }

                }
            }
        }
    }
}