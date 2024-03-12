package com.da_chelimo.ig_clone.ui.screens.account.sign_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.da_chelimo.ig_clone.R
import com.da_chelimo.ig_clone.models.Screens
import com.da_chelimo.ig_clone.ui.components.sign_in.PasswordTextField
import com.da_chelimo.ig_clone.ui.components.sign_in.SignInButton
import com.da_chelimo.ig_clone.ui.components.sign_in.UsernameTextField
import com.da_chelimo.ig_clone.ui.theme.BrightBlue
import com.da_chelimo.ig_clone.ui.theme.SignInBlue
import com.da_chelimo.ig_clone.utils.validateUserName

@Composable
fun SignInScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SignInBlue)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ig_logo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(100.dp)
                .size(80.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            val username = rememberSaveable { mutableStateOf("") }

            UsernameTextField(
                label = "Email address or mobile number",
                username = username,
                modifier = Modifier.padding(horizontal = 12.dp),
                isError = validateUserName(username.value) != null,
                errorText = validateUserName(username.value) ?: "",
                shouldCheckForError = true
            )

            val password = rememberSaveable { mutableStateOf("") }
            PasswordTextField(
                label = "Password",
                password = password,
                performLogin = {},
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(top = 16.dp)
            )

            SignInButton(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(top = 16.dp),
                buttonText = "Log In",
                textColor = Color.White,
                containerColor = BrightBlue,
                outlineColor = Color.Transparent,
                onClick = {}
            )

            SignInButton(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(top = 16.dp),
                buttonText = "Forgotten Password",
                textColor = Color.White,
                containerColor = Color.Transparent,
                outlineColor = Color.Transparent,
                elevation = 0.dp,
                onClick = {}
            )
        }

        SignInButton(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            buttonText = "Create New Account",
            textColor = BrightBlue,
            containerColor = Color.Transparent,
            outlineColor = BrightBlue,
            onClick = {
                navController.navigate(Screens.CreateAccountWithEmail.navigateHere())
            }
        )
    }
}

@Preview
@Composable
fun PreviewSignInScreen() {
    SignInScreen(rememberNavController())
}


