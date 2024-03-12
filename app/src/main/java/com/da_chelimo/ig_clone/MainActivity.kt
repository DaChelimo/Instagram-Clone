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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.da_chelimo.ig_clone.models.NavigationItem
import com.da_chelimo.ig_clone.models.Screens
import com.da_chelimo.ig_clone.models.SignInOptions
import com.da_chelimo.ig_clone.ui.screens.account.create_account.ConfirmationCodeScreen
import com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account.CreateAccountWithEmail
import com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account.CreateAccountWithNumber
import com.da_chelimo.ig_clone.ui.screens.account.sign_in.SignInScreen
import com.da_chelimo.ig_clone.ui.screens.home.HomeScreen
import com.da_chelimo.ig_clone.ui.screens.profile.ProfileScreen
import com.da_chelimo.ig_clone.ui.theme.IGCloneTheme
import com.da_chelimo.ig_clone.utils.getSerializableCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                            HomeScreen()
                        }

                        composable(Screens.SignInScreen.getNavRoute()) {
                            SignInScreen(navController)
                        }

                        composable(Screens.CreateAccountWithEmail.getNavRoute()) {
                            CreateAccountWithEmail(navigateToConfirmationScreen = { email, signInOptions ->
                                navController.navigate(
                                    Screens.ConfirmationCode.navigateHere(email, signInOptions)
                                )
                            })
                        }

                        composable(Screens.CreateAccountWithNumber.getNavRoute()) {
                            CreateAccountWithNumber(navigateToConfirmationScreen = { number, signInOptions ->
                                navController.navigate(
                                    Screens.ConfirmationCode.navigateHere(number, signInOptions)
                                )
                            })
                        }


                        // Composable with Arguments
                        composable(Screens.Profile.getNavRoute()) { navBackStackEntry ->
                            val userID = navBackStackEntry.arguments?.getString("userID")!!
                            ProfileScreen(userID = userID, navController = navController)
                        }

                        composable(Screens.ConfirmationCode.getNavRoute()) { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email")!!
                            val signInOptions = backStackEntry.arguments?.getSerializableCompat(
                                "signInOptions",
                                SignInOptions::class.java
                            )!!

                            ConfirmationCodeScreen(
                                emailOrNumber = email,
                                signInOptions = signInOptions,
                                coroutineScope = coroutineScope
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