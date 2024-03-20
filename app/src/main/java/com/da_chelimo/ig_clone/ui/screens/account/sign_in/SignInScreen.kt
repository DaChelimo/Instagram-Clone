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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.da_chelimo.ig_clone.R
import com.da_chelimo.ig_clone.models.OldScreens
import com.da_chelimo.ig_clone.navigation.JetNavController
import com.da_chelimo.ig_clone.navigation.rememberJetNavController
import com.da_chelimo.ig_clone.ui.components.sign_in.PasswordTextField
import com.da_chelimo.ig_clone.ui.components.sign_in.SignInButton
import com.da_chelimo.ig_clone.ui.components.sign_in.UsernameTextField
import com.da_chelimo.ig_clone.ui.theme.BrightBlue
import com.da_chelimo.ig_clone.ui.theme.SignInBlue
import com.da_chelimo.ig_clone.utils.validateUserName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(jetNavController: JetNavController, coroutineScope: CoroutineScope) {
    val viewModel = viewModel<SignInViewModel>()

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
            var shouldCheckForError by remember {
                mutableStateOf(false)
            }

            UsernameTextField(
                label = "Email address or mobile number",
                username = username,
                modifier = Modifier.padding(horizontal = 12.dp),
                isError = validateUserName(username.value) != null,
                errorText = validateUserName(username.value) ?: "",
                shouldCheckForError = shouldCheckForError
            )

            val password = rememberSaveable { mutableStateOf("") }
            PasswordTextField(
                label = "Password",
                password = password,
                performLogin = {},
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            )

            SignInButton(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(top = 20.dp),
                buttonText = "Log In",
                textColor = Color.White,
                containerColor = BrightBlue,
                outlineColor = Color.Transparent,
                onClick = {
                    shouldCheckForError = true

                    if (validateUserName(username.value) == null)
                        coroutineScope.launch {
                            viewModel.signInWithEmailAndPassword(username.value, password.value)
                        }
                }
            )

            SignInButton(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
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
            textColor = Color.White,
            containerColor = SignInBlue,
            outlineColor = BrightBlue,
            onClick = {
                jetNavController.navigateToCreateAccountWithEmail()
            }
        )
    }
}

@Preview
@Composable
fun PreviewSignInScreen() {
    SignInScreen(rememberJetNavController(), rememberCoroutineScope())
}


