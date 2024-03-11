package com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.da_chelimo.ig_clone.models.SignInOptions
import com.da_chelimo.ig_clone.utils.verifyEmail
import com.da_chelimo.ig_clone.utils.verifyNumber

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
    CreateAccountScreen(
        mainTitle = "What's your mobile number?",
        description = "Enter the mobile number on which you can be contacted. No one will see this on your profile.",
        signUpLabel = "Mobile number",
        isError = { number -> verifyNumber(number) },
        errorText = "Enter a valid mobile number",
        onProceedWithSignUp = { number ->
                  navigateToConfirmationScreen(number, SignInOptions.NUMBER)
        },
        alternativeSignUpText = "Sign up with email address",
        onUseAlternativeSingUp = {}
    )
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
    CreateAccountScreen(
        mainTitle = "What's your email address?",
        description = "Enter the email address on which you can be contacted. No one will see this on your profile.",
        signUpLabel = "Email address",
        isError = { email -> verifyEmail(email) },
        errorText = "Enter a valid email address",
        onProceedWithSignUp = { email ->
                              navigateToConfirmationScreen(email, SignInOptions.EMAIL)
        },
        alternativeSignUpText = "Sign up with mobile number",
        onUseAlternativeSingUp = {}
    )
}

@Preview
@Composable
fun PreviewCreateAccountWithEmail() {
    CreateAccountWithEmail({_,_ -> })
}