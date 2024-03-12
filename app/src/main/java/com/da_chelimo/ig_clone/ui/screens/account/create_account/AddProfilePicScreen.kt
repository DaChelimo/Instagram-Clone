package com.da_chelimo.ig_clone.ui.screens.account.create_account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.da_chelimo.ig_clone.models.Screens
import com.da_chelimo.ig_clone.ui.components.CircularUserImage
import com.da_chelimo.ig_clone.ui.components.sign_in.SignInButton
import com.da_chelimo.ig_clone.ui.theme.BrightBlue
import com.da_chelimo.ig_clone.ui.theme.PoppinsFont
import com.da_chelimo.ig_clone.ui.theme.SignInBlue
import com.da_chelimo.ig_clone.ui.theme.TextFieldUnfocusedLabelBlue

@Composable
fun AddProfilePicScreen(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(SignInBlue)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "Add a profile picture",
                fontSize = 17.sp,
                fontFamily = PoppinsFont,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "Add a profile picture so that your friends know it's you. Everyone will be able to see your picture.",
                fontSize = 13.sp,
                color = Color.White,
                fontFamily = PoppinsFont,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )

            CircularUserImage(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .align(Alignment.CenterHorizontally),
                userIcon = null,
                size = 110.dp
            )
        }


        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(bottom = 16.dp)
        ) {
//            SignInOrUpButton(buttonText = ) {
            SignInButton(
                buttonText = "Add Picture",
                textColor = Color.White,
                containerColor = BrightBlue,
                outlineColor = BrightBlue,
                onClick = {}
            )

            SignInButton(
                modifier = Modifier,
                buttonText = "Skip",
                textColor = TextFieldUnfocusedLabelBlue,
                containerColor = Color.Transparent,
                outlineColor = TextFieldUnfocusedLabelBlue,
                onClick = {
                    navController.navigate(Screens.Home.getNavRoute()) {
//                        popUpTo(Screens.Home.getNavRoute())
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewAddProfilePicScreen() {
    AddProfilePicScreen(rememberNavController())
}