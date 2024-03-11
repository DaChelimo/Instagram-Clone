package com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.da_chelimo.ig_clone.ui.components.sign_in.SignInButton
import com.da_chelimo.ig_clone.ui.components.sign_in.UsernameTextField
import com.da_chelimo.ig_clone.ui.theme.BrightBlue
import com.da_chelimo.ig_clone.ui.theme.PoppinsFont
import com.da_chelimo.ig_clone.ui.theme.SignInBlue
import com.da_chelimo.ig_clone.ui.theme.TextFieldUnfocusedBorderBlue


@Composable
fun CreateAccountScreen(
    mainTitle: String,
    description: String,
    isError: (String) -> Boolean,
    errorText: String,
    signUpLabel: String,
    onProceedWithSignUp: (String) -> Unit,
    alternativeSignUpText: String,
    onUseAlternativeSingUp: () -> Unit
) {
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
            mainTitle = mainTitle,
            description = description,
            backBtnContentDescription = "Return back to Log In Screen"
        )

        val signUpName = remember {
            mutableStateOf("")
        }
        UsernameTextField(
            label = signUpLabel,
            username = signUpName,
            modifier = Modifier.padding(top = 16.dp),
            isError = isError(signUpName.value),
            shouldCheckForError = shouldCheckForError,
            errorText = errorText
        )

        SignInButton(
            modifier = Modifier.padding(top = 16.dp),
            buttonText = "Next",
            textColor = Color.White,
            containerColor = BrightBlue,
            outlineColor = Color.Transparent,
            onClick = {
                shouldCheckForError = true
                onProceedWithSignUp(signUpName.value)
            }
        )

        SignInButton(
            buttonText = alternativeSignUpText,
            textColor = Color.White,
            containerColor = Color.Transparent,
            outlineColor = TextFieldUnfocusedBorderBlue,
            onClick = { onUseAlternativeSingUp() }
        )
    }
}

@Preview
@Composable
fun PreviewCreateAccountScreen() {
    CreateAccountScreen(
        mainTitle = "What's your mobile number?",
        description = "Enter the mobile number on which you can be contacted. No one will see this on your profile.",
        signUpLabel = "Mobile number",
        isError = { false },
        errorText = "Enter a valid mobile number",
        onProceedWithSignUp = {},
        alternativeSignUpText = "Sign up with email address",
        onUseAlternativeSingUp = {}
    )
}


@Composable
fun CreateAccountHeader(modifier: Modifier = Modifier, mainTitle: String, description: String?, backBtnContentDescription: String? = null) {
    Column(modifier = modifier.fillMaxWidth()) {
        Image(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = backBtnContentDescription,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .padding(top = 4.dp)
                .size(34.dp)
                .padding(top = 6.dp, bottom = 6.dp, end = 6.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = mainTitle,
            fontSize = 17.sp,
            fontFamily = PoppinsFont,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp)
        )

        // description is null when creating our full name
        if (description != null) {
            Text(
                text = description,
                fontSize = 13.sp,
                color = Color.White,
                fontFamily = PoppinsFont,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}