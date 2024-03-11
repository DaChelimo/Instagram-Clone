package com.da_chelimo.ig_clone.ui.components.sign_in

import android.view.KeyEvent.ACTION_DOWN
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.da_chelimo.ig_clone.R
import com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account.CreateAccountScreen
import com.da_chelimo.ig_clone.ui.theme.BrightBlue
import com.da_chelimo.ig_clone.ui.theme.ErrorRed
import com.da_chelimo.ig_clone.ui.theme.Grey
import com.da_chelimo.ig_clone.ui.theme.PoppinsFont
import com.da_chelimo.ig_clone.ui.theme.TextFieldBackgroundBlue
import com.da_chelimo.ig_clone.ui.theme.TextFieldFocusedBorderBlue
import com.da_chelimo.ig_clone.ui.theme.TextFieldFocusedLabelBlue
import com.da_chelimo.ig_clone.ui.theme.TextFieldUnfocusedBorderBlue
import com.da_chelimo.ig_clone.ui.theme.TextFieldUnfocusedLabelBlue


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

@Composable
fun UsernameTextField(
    modifier: Modifier = Modifier,
    label: String,
    username: MutableState<String>,
    isError: Boolean,
    errorText: String,
    shouldCheckForError: Boolean
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = username.value,
        onValueChange = { username.value = it },
        placeholder = { Text(text = label, color = Grey) },
        modifier = modifier
            .fillMaxWidth()
            .onPreviewKeyEvent {
                if (it.key == Key.Tab && it.nativeKeyEvent.action == ACTION_DOWN) {
                    focusManager.moveFocus(FocusDirection.Down)
                    true
                } else {
                    false
                }
            },
        colors = getSignInTextFieldColors(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
        supportingText = {
            if (isError)
                Text(text = errorText, fontSize = 11.sp, fontFamily = PoppinsFont)
        }, isError = isError && shouldCheckForError,
        singleLine = true,
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        trailingIcon = {
            if (username.value.isNotEmpty())
                ClearFieldButton {
                    // Once the clear button is clicked, rest the field to empty
                    username.value = ""
                }
        }
    )
}

@Composable
fun ClearFieldButton(modifier: Modifier = Modifier, clearField: () -> Unit) {
    Icon(
        Icons.Default.Clear,
        "Clear field",
        tint = Color.White,
        modifier = modifier
            .size(20.dp)
            .clickable {
                clearField()
            }
    )
}

@Composable
fun getSignInTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = TextFieldFocusedBorderBlue,
    unfocusedBorderColor = TextFieldUnfocusedBorderBlue,
    focusedContainerColor = TextFieldBackgroundBlue,
    unfocusedContainerColor = TextFieldBackgroundBlue,
    focusedLabelColor = TextFieldFocusedLabelBlue,
    unfocusedLabelColor = TextFieldUnfocusedLabelBlue,
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    cursorColor = Color.White,
    errorBorderColor = ErrorRed,
    errorSupportingTextColor = ErrorRed
)

@Preview
@Composable
fun PreviewUsernameTextField() {
    val username = rememberSaveable { mutableStateOf("") }

    UsernameTextField(
        label = "Username", username = username,
        modifier = Modifier.padding(horizontal = 12.dp),
        isError = false,
        shouldCheckForError = false,
        errorText = "Enter a valid mobile number",
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    label: String,
    password: MutableState<String>,
    performLogin: () -> Unit
) {
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = password.value,
        onValueChange = { password.value = it },
        placeholder = { Text(text = label, color = Grey) },
        modifier = modifier
            .fillMaxWidth(),
        colors = getSignInTextFieldColors(),
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        keyboardActions = KeyboardActions(onDone = {
            performLogin()
        }),
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { isPasswordVisible = !isPasswordVisible },
                painter = painterResource(
                    id = if (isPasswordVisible) R.drawable.password_visible else R.drawable.password_hidden
                ),
                contentDescription = if (isPasswordVisible) "Hide password" else "Show password"

            )
        }
    )
}

@Preview
@Composable
fun PreviewPasswordTextField() {
    val password = rememberSaveable { mutableStateOf("") }
    PasswordTextField(label = "Password", password = password, performLogin = {})
}

@Composable
fun SignInButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    textColor: Color,
    containerColor: Color,
    outlineColor: Color,
    elevation: Dp = 2.dp,
    onClick: () -> Unit
) {
    TextButton(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(32.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = textColor,
            containerColor = containerColor
        ),
        border = BorderStroke(1.dp, outlineColor),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = elevation)
    ) {
        Text(
            text = buttonText,
            fontFamily = PoppinsFont,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
    }
}


@Preview
@Composable
fun PreviewSignInOutlinedButton() {
    SignInButton(
        buttonText = "Create new account",
        textColor = BrightBlue,
        containerColor = Color.Transparent,
        outlineColor = BrightBlue,
        onClick = {}
    )
}

@Preview
@Composable
fun PreviewSignInNoOutlineButton() {
    SignInButton(
        buttonText = "Create new account",
        textColor = BrightBlue,
        containerColor = Color.Transparent,
        outlineColor = Color.Transparent,
        onClick = {}
    )
}

@Preview
@Composable
fun PreviewSignInOrUpButton() {
    SignInButton(
        buttonText = "Log In",
        textColor = Color.White,
        containerColor = BrightBlue,
        outlineColor = Color.Transparent,
        onClick = {}
    )
}


