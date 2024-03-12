package com.da_chelimo.ig_clone.ui.screens.account.create_account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.da_chelimo.ig_clone.ui.components.sign_in.CreateAccountHeader
import com.da_chelimo.ig_clone.ui.components.sign_in.PasswordTextField
import com.da_chelimo.ig_clone.ui.components.sign_in.SignInButton
import com.da_chelimo.ig_clone.ui.theme.BrightBlue
import com.da_chelimo.ig_clone.ui.theme.SignInBlue

@Composable
fun CreatePasswordScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SignInBlue)
            .padding(horizontal = 8.dp)
    ) {
        var shouldCheckForError by remember {
            mutableStateOf(false)
        }

        CreateAccountHeader(
            modifier = Modifier.padding(top = 4.dp),
            mainTitle = "Create a password",
            description = "Create a password with a least 6 letters or numbers. It should be something that others can't guess.",
            navController = navController,
            backBtnContentDescription = "Go Back Button"
        )

        val password = remember {
            mutableStateOf("")
        }

        PasswordTextField(
            modifier = Modifier.padding(top = 16.dp),
            label = "Password",
            password = password,
            performLogin = {
//                TODO: performLogin()
            }
        )

        SignInButton(
            modifier = Modifier.padding(top = 16.dp),
            buttonText = "Next",
            textColor = Color.White,
            containerColor = BrightBlue,
            outlineColor = Color.Transparent,
            onClick = {
                shouldCheckForError = true
//                onProceedWithSignUp(signUpName.value)
            }
        )

    }
}

@Preview
@Composable
fun PreviewCreatePasswordScreen() {
    CreatePasswordScreen(rememberNavController())
}