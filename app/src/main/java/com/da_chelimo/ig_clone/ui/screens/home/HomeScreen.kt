package com.da_chelimo.ig_clone.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.da_chelimo.ig_clone.R
import com.da_chelimo.ig_clone.models.Screens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { _ ->
        Box {
            Image(
                painter = painterResource(id = R.drawable.instagram_text),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(top = 4.dp, start = 4.dp)
                    .height(48.dp)
                    .align(Alignment.TopStart)
            )

            LazyColumn(modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxSize()) {
                // TODO: Set up posts
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {

}

@Composable
fun BottomNavBar(modifier: Modifier = Modifier, navController: NavController) {
    BottomAppBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavItem(
            currentRoute = currentRoute,
            screen = Screens.Home,
            navController = navController,
            icon = Icons.Default.Home,
            iconDescription = "Home"
        )

        BottomNavItem(
            currentRoute = currentRoute,
            screen = Screens.Search,
            navController = navController,
            icon = Icons.Default.Search,
            iconDescription = "Search"
        )

        BottomNavItem(
            currentRoute = currentRoute,
            screen = Screens.Profile,
            navController = navController,
            icon = Icons.Outlined.AccountCircle,
            iconDescription = "Profile",
            Firebase.auth.uid!!
        )
    }
}

@Composable
fun RowScope.BottomNavItem(
    currentRoute: String?,
    screen: Screens,
    navController: NavController,
    icon: ImageVector,
    iconDescription: String,
    vararg extraNavArgs: String
) {
    BottomNavigationItem(
        selected = currentRoute == screen.getNavRoute(),
        onClick = {
            navController.navigate(screen.navigateHere(*extraNavArgs)) {
//                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        },
        icon = {

            Icon(
                imageVector = icon,
                contentDescription = iconDescription
            )
        }
    )
}


