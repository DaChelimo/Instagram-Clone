package com.da_chelimo.ig_clone.ui.screens.account.create_account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.da_chelimo.ig_clone.ui.components.sign_in.ClearFieldButton
import com.da_chelimo.ig_clone.ui.components.sign_in.SignInButton
import com.da_chelimo.ig_clone.ui.components.sign_in.getSignInTextFieldColors
import com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account.CreateAccountHeader
import com.da_chelimo.ig_clone.ui.theme.BrightBlue
import com.da_chelimo.ig_clone.ui.theme.SignInBlue

@Composable
fun EnterNameScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SignInBlue)
            .padding(horizontal = 8.dp)
    ) {
        CreateAccountHeader(
            modifier = Modifier.padding(top = 4.dp),
            mainTitle = "What's your name?",
            description = null,
            backBtnContentDescription = "Go Back Button"
        )

        var fullName by remember {
            mutableStateOf("")
        }

        OutlinedTextField(
            modifier = Modifier.padding(top = 16.dp),
            label = { Text("Full name") },
            placeholder = { Text("Full name") },
            value = fullName,
            onValueChange = { fullName = it },
            colors = getSignInTextFieldColors(),
            trailingIcon = {
                if (fullName.isNotEmpty())
                    ClearFieldButton {
                        fullName = ""
                    }
            }
        )

        SignInButton(
            modifier = Modifier.padding(top = 16.dp),
            buttonText = "Next",
            textColor = Color.White,
            containerColor = BrightBlue,
            outlineColor = Color.Transparent,
            onClick = {
                fullName = fullName.trim()
//                onProceedWithSignUp(signUpName.value)
            }
        )
    }
}

@Preview
@Composable
fun PreviewEnterNameScreen() {
    EnterNameScreen()
}

