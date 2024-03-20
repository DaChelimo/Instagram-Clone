package com.da_chelimo.ig_clone.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.da_chelimo.ig_clone.R
import com.da_chelimo.ig_clone.models.OldScreens
import com.da_chelimo.ig_clone.navigation.JetNavController
import com.da_chelimo.ig_clone.navigation.Screens
import com.da_chelimo.ig_clone.navigation.rememberJetNavController
import com.da_chelimo.ig_clone.ui.components.LargeImagePost
import com.da_chelimo.ig_clone.ui.theme.TextFieldUnfocusedLabelBlue
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(jetNavController: JetNavController, coroutineScope: CoroutineScope) {
    val viewModel = viewModel<HomeViewModel>()
    val posts by viewModel.posts.observeAsState(initial = listOf())

    Scaffold(
        bottomBar = {
            BottomNavBar(jetNavController = jetNavController)
        }
    ) { _ ->
        Box {
            Image(
                painter = painterResource(id = R.drawable.instagram_text),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(top = 4.dp, start = 8.dp)
                    .height(52.dp)
                    .align(Alignment.TopStart),
                contentScale = ContentScale.Crop
            )

            LazyColumn(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxSize()
            ) {
                // TODO: Set up posts
                items(posts) { post ->
                    LargeImagePost(
                        imagePost = post,
                        currentUser = viewModel.currentUser,
                        uploadComment = {},
                        coroutineScope = coroutineScope,
                        fetchComments = {
                            listOf()
                        },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(jetNavController = rememberJetNavController(), coroutineScope = rememberCoroutineScope())
}

@Composable
fun BottomNavBar(modifier: Modifier = Modifier, jetNavController: JetNavController) {
    BottomAppBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White
    ) {
        BottomNavItem(
            currentScreen = Screens.HOME,
            jetNavController = jetNavController,
            icon = Icons.Default.Home,
            iconDescription = "Home"
        )

        BottomNavItem(
            currentScreen = Screens.SEARCH,
            jetNavController = jetNavController,
            icon = Icons.Default.Search,
            iconDescription = "Search"
        )

        BottomNavItem(
            currentScreen = Screens.PROFILE,
            jetNavController = jetNavController,
            icon = Icons.Outlined.AccountCircle,
            iconDescription = "Profile"
        )
    }
}

@Composable
fun RowScope.BottomNavItem(
    currentScreen: String,
    jetNavController: JetNavController,
    icon: ImageVector,
    iconDescription: String
) {
    val isSelected = jetNavController.currentRoute == currentScreen

    BottomNavigationItem(
        selected = isSelected,
        onClick = {
            jetNavController.navigateToBottomBarRoute(currentScreen)
        },
        icon = {
            val iconModifier = Modifier
            if (isSelected) iconModifier.size(32.dp)
            else iconModifier.size(26.dp)

            Icon(
                imageVector = icon,
                contentDescription = iconDescription
            )
        },
        selectedContentColor = Color.White,
        unselectedContentColor = TextFieldUnfocusedLabelBlue
    )
}


