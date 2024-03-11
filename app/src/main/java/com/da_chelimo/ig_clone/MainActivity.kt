package com.da_chelimo.ig_clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.da_chelimo.ig_clone.models.NavigationItem
import com.da_chelimo.ig_clone.ui.screens.home.HomeScreen
import com.da_chelimo.ig_clone.ui.screens.profile.ProfileScreen
import com.da_chelimo.ig_clone.ui.theme.IGCloneTheme

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

                    NavHost(
                        navController = navController,
                        startDestination = NavigationItem.Home.route
                    ) {
                        composable(NavigationItem.Home.route) {
                            HomeScreen()
                        }

                        composable("profile/userID") {navBackStackEntry ->
                            val userID = navBackStackEntry.arguments?.getString("userID")!!
                            ProfileScreen(userID = userID, navController = navController)
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