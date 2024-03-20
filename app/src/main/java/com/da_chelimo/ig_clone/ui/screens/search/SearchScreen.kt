package com.da_chelimo.ig_clone.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.da_chelimo.ig_clone.navigation.JetNavController
import com.da_chelimo.ig_clone.ui.screens.home.BottomNavBar
import com.da_chelimo.ig_clone.ui.theme.SignInBlue

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(jetNavController: JetNavController) {
    Scaffold(bottomBar = { BottomNavBar(jetNavController = jetNavController) }) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SignInBlue)
        ) {

        }
    }
}