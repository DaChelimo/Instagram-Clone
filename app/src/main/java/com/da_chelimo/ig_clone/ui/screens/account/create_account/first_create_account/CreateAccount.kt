package com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.da_chelimo.ig_clone.models.Screens
import com.da_chelimo.ig_clone.ui.components.sign_in.CreateAccountHeader
import com.da_chelimo.ig_clone.ui.components.sign_in.PasswordTextField
import com.da_chelimo.ig_clone.ui.components.sign_in.SignInButton
import com.da_chelimo.ig_clone.ui.components.sign_in.UsernameTextField
import com.da_chelimo.ig_clone.ui.theme.BrightBlue
import com.da_chelimo.ig_clone.ui.theme.ErrorRed
import com.da_chelimo.ig_clone.ui.theme.SignInBlue
import com.da_chelimo.ig_clone.ui.theme.TextFieldBackgroundBlue
import com.da_chelimo.ig_clone.ui.theme.TextFieldFocusedBorderBlue
import com.da_chelimo.ig_clone.ui.theme.TextFieldFocusedLabelBlue
import com.da_chelimo.ig_clone.ui.theme.TextFieldUnfocusedBorderBlue
import com.da_chelimo.ig_clone.ui.theme.TextFieldUnfocusedLabelBlue
import com.da_chelimo.ig_clone.utils.getActivity
import com.da_chelimo.ig_clone.utils.verifyEmailIsValid
import com.da_chelimo.ig_clone.utils.verifyPassword
import com.togitech.ccp.component.TogiCountryCodePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

/*
Param:
navigateToConfirmationScreen contains:
(a) String -> number or email
(b) Boolean -> isNumber
 */
@Composable
fun CreateAccountWithNumber(
    navController: NavController
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
            navController = navController,
            backBtnContentDescription = "Return back to Log In Screen"
        )

        var fullPhoneNumber by rememberSaveable {
            mutableStateOf("")
        }


        var phoneNumber: String by rememberSaveable { mutableStateOf("") }
        var isNumberValid: Boolean by rememberSaveable { mutableStateOf(false) }

        TogiCountryCodePicker(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 2.dp),
            shape = RoundedCornerShape(4.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = TextFieldFocusedBorderBlue,
                unfocusedBorderColor = TextFieldUnfocusedBorderBlue,
                focusedLabelColor = TextFieldFocusedLabelBlue,
                unfocusedLabelColor = TextFieldUnfocusedLabelBlue,
                cursorColor = Color.White,
                errorBorderColor = ErrorRed,
                textColor = Color.White,
                backgroundColor = TextFieldBackgroundBlue,
                errorLabelColor = ErrorRed,
                placeholderColor = TextFieldFocusedBorderBlue,
                trailingIconColor = if (isNumberValid) TextFieldFocusedBorderBlue else ErrorRed
            ),
            onValueChange = { (code, phone), isValid ->
                Timber.d("onValueChange: $code $phone -> $isValid")

                phoneNumber = phone
                fullPhoneNumber = code + phone
                isNumberValid = isValid
            },
            label = { Text("Mobile number", fontSize = 11.sp) }
        )

        SignInButton(
            modifier = Modifier.padding(top = 16.dp),
            buttonText = "Next",
            textColor = Color.White,
            containerColor = BrightBlue,
            outlineColor = Color.Transparent,
            onClick = {
                shouldCheckForError = true

                if (isNumberValid) {
                    viewModel.signInWithNumber(fullPhoneNumber, activity)
                    navController.navigate(
                        Screens.ConfirmationCode.navigateHere(fullPhoneNumber)
                    )
                }
            }
        )

        SignInButton(
            buttonText = "Sign up with email address",
            textColor = Color.White,
            containerColor = Color.Transparent,
            outlineColor = TextFieldUnfocusedBorderBlue,
            onClick = { navController.popBackStack() }
        )
    }
}

@Preview
@Composable
fun PreviewCreateAccountWithNumber() {
    CreateAccountWithNumber(navController = rememberNavController())
}


@Composable
fun CreateAccountWithEmail(
    navController: NavHostController,
    coroutineScope: CoroutineScope
) {
    val viewModel = viewModel<CreateAccountViewModel>()
    val activity = LocalContext.current.getActivity()

    // Change system bar to match background color
//    ChangeSystemBarColors(systemBarColor = SignInBlue, isDarkTheme = true, view = LocalView.current)

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
            navController = navController,
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
            isError = !verifyEmailIsValid(email.value),
            shouldCheckForError = shouldCheckForError,
            errorText = "Enter a valid email address"
        )

        PasswordTextField(
            label = "Password",
            password = password,
            performLogin = {
                shouldCheckForError = true

                if (verifyEmailIsValid(email.value) && verifyPassword(password.value)) {
                    viewModel.signInWithNumber(email.value, activity)
                    navController.navigate(
                        Screens.ConfirmationCode.navigateHere(email.value, password.value)
                    )
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

                if (verifyEmailIsValid(email.value) && verifyPassword(password.value)) {
                    coroutineScope.launch {
                        viewModel.signInWithEmail(email.value, password.value, activity)
                        navController.navigate(
                            Screens.DateOfBirth.navigateHere()
                        )
                    }
                }
            }
        )

        SignInButton(
            buttonText = "Sign up with mobile number",
            textColor = Color.White,
            containerColor = Color.Transparent,
            outlineColor = TextFieldUnfocusedBorderBlue,
            onClick = {
                navController.navigate(
                    Screens.CreateAccountWithNumber.navigateHere()
                )
            }
        )
    }
}

@Preview
@Composable
fun PreviewCreateAccountWithEmail() {
    CreateAccountWithEmail(rememberNavController(), rememberCoroutineScope())
}