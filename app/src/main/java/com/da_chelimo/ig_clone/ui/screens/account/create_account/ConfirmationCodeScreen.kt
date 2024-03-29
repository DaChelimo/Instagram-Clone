package com.da_chelimo.ig_clone.ui.screens.account.create_account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.da_chelimo.ig_clone.models.OldScreens
import com.da_chelimo.ig_clone.navigation.JetNavController
import com.da_chelimo.ig_clone.navigation.rememberJetNavController
import com.da_chelimo.ig_clone.ui.components.sign_in.CreateAccountHeader
import com.da_chelimo.ig_clone.ui.components.sign_in.SignInButton
import com.da_chelimo.ig_clone.ui.components.sign_in.getSignInTextFieldColors
import com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account.CreateAccountViewModel
import com.da_chelimo.ig_clone.ui.theme.BrightBlue
import com.da_chelimo.ig_clone.ui.theme.Grey
import com.da_chelimo.ig_clone.ui.theme.PoppinsFont
import com.da_chelimo.ig_clone.ui.theme.SignInBlue
import com.da_chelimo.ig_clone.ui.theme.TextFieldUnfocusedBorderBlue
import com.da_chelimo.ig_clone.utils.verifyCodeLength
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun ConfirmationCodeScreen(
    number: String,
    coroutineScope: CoroutineScope,
    jetNavController: JetNavController
) {
    val viewModel = viewModel<CreateAccountViewModel>()

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
            modifier = Modifier,
            mainTitle = "Enter the confirmation code",
            description = "To confirm your account, enter the 6-digit code that we sent to $number",
            jetNavController = jetNavController,
            backBtnContentDescription = "Go Back Button"
        )

        val code = remember {
            mutableStateOf("")
        }

        CodeTextField(
            label = "Confirmation Code",
            code = code,
            modifier = Modifier.padding(top = 16.dp),
            isError = verifyCodeLength(code.value),
            shouldCheckForError = shouldCheckForError,
            errorText = "Code should be 6-digits long",
            processCode = { smsCode ->
                coroutineScope.launch {
                    try {
                        viewModel.verifyConfirmationCode(smsCode)
                    } catch (e: Exception) {
                        Timber.e(e)
                    }
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
                coroutineScope.launch {
                    try {
                        viewModel.verifyConfirmationCode(code.value)
                        jetNavController.navigateToDateOfBirth()
                    } catch (e: Exception) {
                        Timber.e(e)
                    }
                }
            }
        )

        SignInButton(
            buttonText = "I didn't receive the code",
            textColor = Color.White,
            containerColor = Color.Transparent,
            outlineColor = TextFieldUnfocusedBorderBlue,
            onClick = {
//                onUseAlternativeSingUp()
                // TODO: Open a tab for resending the code
            }
        )
    }
}


@Composable
fun CodeTextField(
    modifier: Modifier = Modifier,
    label: String,
    code: MutableState<String>,
    isError: Boolean,
    errorText: String,
    shouldCheckForError: Boolean,
    processCode: (String) -> Unit
) {
    OutlinedTextField(
        value = code.value,
        onValueChange = { code.value = it },
        placeholder = { Text(text = label, color = Grey) },
        modifier = modifier.fillMaxWidth(),
        colors = getSignInTextFieldColors(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.NumberPassword
        ),
        supportingText = {
            if (isError)
                Text(text = errorText, fontSize = 11.sp, fontFamily = PoppinsFont)
        }, isError = isError && shouldCheckForError,
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = {
            processCode(code.value)
        })
    )
}


@Preview
@Composable
fun PreviewConfirmationCode() {
    ConfirmationCodeScreen(
        number = "andrewchelimo2000@gmail.com",
        coroutineScope = rememberCoroutineScope(),
        jetNavController = rememberJetNavController()
    )
}