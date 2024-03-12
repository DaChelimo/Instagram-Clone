package com.da_chelimo.ig_clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.da_chelimo.ig_clone.models.Screens
import com.da_chelimo.ig_clone.ui.screens.account.create_account.AddProfilePicScreen
import com.da_chelimo.ig_clone.ui.screens.account.create_account.ConfirmationCodeScreen
import com.da_chelimo.ig_clone.ui.screens.account.create_account.create_username.CreateUsernameScreen
import com.da_chelimo.ig_clone.ui.screens.account.create_account.DateOfBirthScreen
import com.da_chelimo.ig_clone.ui.screens.account.create_account.enter_name.EnterNameScreen
import com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account.CreateAccountWithEmail
import com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account.CreateAccountWithNumber
import com.da_chelimo.ig_clone.ui.screens.account.sign_in.SignInScreen
import com.da_chelimo.ig_clone.ui.screens.home.HomeScreen
import com.da_chelimo.ig_clone.ui.screens.profile.ProfileScreen
import com.da_chelimo.ig_clone.ui.screens.search.SearchScreen
import com.da_chelimo.ig_clone.ui.theme.IGCloneTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            IGCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val coroutineScope = rememberCoroutineScope()

                    NavHost(
                        navController = navController,
                        startDestination = if (Firebase.auth.currentUser != null) Screens.Home.getNavRoute() else Screens.SignInScreen.getNavRoute()
                    ) {
                        composable(Screens.Home.getNavRoute()) {
                            HomeScreen(navController)
                        }
                        composable(Screens.Search.getNavRoute()) {
                            SearchScreen()
                        }

                        composable(Screens.SignInScreen.getNavRoute()) {
                            SignInScreen(navController)
                        }

                        composable(Screens.CreateAccountWithEmail.getNavRoute()) {
                            CreateAccountWithEmail(navController, coroutineScope)
                        }

                        composable(Screens.CreateAccountWithNumber.getNavRoute()) {
                            CreateAccountWithNumber(navController)
                        }

                        composable(Screens.DateOfBirth.getNavRoute()) {
                            DateOfBirthScreen(navController)
                        }
                        composable(Screens.CreateUsername.getNavRoute()) { backStackEntry ->
                            val dateOfBirth = backStackEntry.arguments?.getString("DOB")?.toLong()!!

                            CreateUsernameScreen(navController, dateOfBirth)
                        }
                        composable(Screens.EnterNameScreen.getNavRoute()) {
                            EnterNameScreen(navController)
                        }
                        composable(Screens.AddProfilePicScreen.getNavRoute()) {
                            AddProfilePicScreen(navController)
                        }



                        // Composable with Arguments
                        composable(Screens.Profile.getNavRoute()) { navBackStackEntry ->
                            val userID = navBackStackEntry.arguments?.getString("userID")!!
                            ProfileScreen(userID = userID, navController = navController)
                        }

                        composable(Screens.ConfirmationCode.getNavRoute()) { backStackEntry ->
                            val number = backStackEntry.arguments?.getString("number")!!

                            ConfirmationCodeScreen(
                                number = number,
                                coroutineScope = coroutineScope,
                                navController = navController
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IGCloneTheme {
        Greeting("Android")
    }
}