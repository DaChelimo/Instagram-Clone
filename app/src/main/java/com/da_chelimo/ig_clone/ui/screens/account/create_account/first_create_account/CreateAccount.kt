package com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.da_chelimo.ig_clone.models.SignInOptions
import com.da_chelimo.ig_clone.ui.components.sign_in.PasswordTextField
import com.da_chelimo.ig_clone.ui.components.sign_in.SignInButton
import com.da_chelimo.ig_clone.ui.components.sign_in.UsernameTextField
import com.da_chelimo.ig_clone.ui.theme.BrightBlue
import com.da_chelimo.ig_clone.ui.theme.SignInBlue
import com.da_chelimo.ig_clone.ui.theme.TextFieldUnfocusedBorderBlue
import com.da_chelimo.ig_clone.utils.getActivity
import com.da_chelimo.ig_clone.utils.verifyEmail
import com.da_chelimo.ig_clone.utils.verifyNumber
import com.da_chelimo.ig_clone.utils.verifyPassword

/*
Param:
navigateToConfirmationScreen contains:
(a) String -> number or email
(b) Boolean -> isNumber
 */
@Composable
fun CreateAccountWithNumber(
    navigateToConfirmationScreen: (String, SignInOptions) -> Unit
) {
    val viewModel = viewModel<CreateAccountViewModel>()
    val activity = LocalContext.current.getActivity()

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
            mainTitle = "What's your mobile number?",
            description = "Enter the mobile number on which you can be contacted. No one will see this on your profile.",
            backBtnContentDescription = "Return back to Log In Screen"
        )

        val number = remember {
            mutableStateOf("")
        }

        UsernameTextField(
            label = "Mobile number",
            username = number,
            modifier = Modifier.padding(top = 16.dp),
            isError = verifyNumber(number.value),
            shouldCheckForError = shouldCheckForError,
            errorText = "Enter a valid mobile number"
        )

        SignInButton(
            modifier = Modifier.padding(top = 16.dp),
            buttonText = "Next",
            textColor = Color.White,
            containerColor = BrightBlue,
            outlineColor = Color.Transparent,
            onClick = {
                shouldCheckForError = true
                viewModel.signInWithNumber(number.value, activity)
                navigateToConfirmationScreen(number.value, SignInOptions.NUMBER)
            }
        )

        SignInButton(
            buttonText = "Sign up with email address",
            textColor = Color.White,
            containerColor = Color.Transparent,
            outlineColor = TextFieldUnfocusedBorderBlue,
            onClick = {

            }
        )
    }
}

@Preview
@Composable
fun PreviewCreateAccountWithNumber() {
    CreateAccountWithNumber(navigateToConfirmationScreen = { a, b -> })
}


@Composable
fun CreateAccountWithEmail(
    navigateToConfirmationScreen: (String, SignInOptions) -> Unit
) {
    val viewModel = viewModel<CreateAccountViewModel>()
    val activity = LocalContext.current.getActivity()

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
            mainTitle = "What's your email address?",
            description = "Enter the email address on which you can be contacted. No one will see this on your profile.",
            backBtnContentDescription = "Return back to Log In Screen"
        )

        val email = remember {
            mutableStateOf("")
        }
        val password = remember {
            mutableStateOf("")
        }

        UsernameTextField(
            label = "Email address",
            username = email,
            modifier = Modifier.padding(top = 16.dp),
            isError = verifyNumber(email.value),
            shouldCheckForError = shouldCheckForError,
            errorText = "Enter a valid email address"
        )

        PasswordTextField(
            label = "Password",
            password = password,
            performLogin = {
                shouldCheckForError = true

                if (verifyEmail(email.value) && verifyPassword(password.value)) {
                    viewModel.signInWithNumber(email.value, activity)
                    navigateToConfirmationScreen(email.value, SignInOptions.NUMBER)
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
                shouldCheckForError = true

                if (verifyEmail(email.value) && verifyPassword(password.value)) {
                    viewModel.signInWithNumber(email.value, activity)
                    navigateToConfirmationScreen(email.value, SignInOptions.NUMBER)
                }
            }
        )

        SignInButton(
            buttonText = "Sign up with mobile number",
            textColor = Color.White,
            containerColor = Color.Transparent,
            outlineColor = TextFieldUnfocusedBorderBlue,
            onClick = {

            }
        )
    }
}

@Preview
@Composable
fun PreviewCreateAccountWithEmail() {
    CreateAccountWithEmail({ _, _ -> })
}