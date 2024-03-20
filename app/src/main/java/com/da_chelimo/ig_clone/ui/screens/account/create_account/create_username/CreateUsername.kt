package com.da_chelimo.ig_clone.ui.screens.account.create_account.create_username

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.da_chelimo.ig_clone.models.OldScreens
import com.da_chelimo.ig_clone.navigation.JetNavController
import com.da_chelimo.ig_clone.navigation.rememberJetNavController
import com.da_chelimo.ig_clone.ui.components.sign_in.CreateAccountHeader
import com.da_chelimo.ig_clone.ui.components.sign_in.SignInButton
import com.da_chelimo.ig_clone.ui.components.sign_in.getSignInTextFieldColors
import com.da_chelimo.ig_clone.ui.theme.BrightBlue
import com.da_chelimo.ig_clone.ui.theme.SignInBlue
import com.da_chelimo.ig_clone.ui.theme.TextFieldUnfocusedBorderBlue
import com.da_chelimo.ig_clone.utils.validateUserName

@Composable
fun CreateUsernameScreen(jetNavController: JetNavController, dateOfBirth: Long) {
    val viewModel = viewModel<CreateUsernameViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SignInBlue)
            .padding(horizontal = 8.dp)
    ) {
        CreateAccountHeader(
            modifier = Modifier.padding(top = 4.dp),
            mainTitle = "Create a  username",
            description = "Add a username. You can change this any time.",
            jetNavController = jetNavController,
            backBtnContentDescription = "Go Back Button"
        )

        var username by remember {
            mutableStateOf("")
        }
        var shouldCheckForError by remember {
            mutableStateOf(false)
        }

        OutlinedTextField(
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
            label = { Text("Username") },
            placeholder = { Text("Username") },
            value = username,
            onValueChange = { username = it },
            colors = getSignInTextFieldColors(),
            isError = validateUserName(username) != null && shouldCheckForError,
            supportingText = {
                val errorText = validateUserName(username)

                if (errorText != null && shouldCheckForError) Text(text = errorText)
            },
            trailingIcon = {
                Icon(
                    imageVector = if (validateUserName(username) == null) Icons.Outlined.CheckCircle else Icons.Default.Clear,
                    contentDescription = if (validateUserName(username) == null) "Username is valid" else "Clear username field",
                    tint = if (validateUserName(username) == null) Color.Green else
                        TextFieldUnfocusedBorderBlue,
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        SignInButton(
            modifier = Modifier.padding(top = 16.dp),
            buttonText = "Next",
            textColor = Color.White,
            containerColor = BrightBlue,
            outlineColor = Color.Transparent,
            onClick = {
                username = username.trim()
                shouldCheckForError = true

                // TODO: Add a check here
                if (validateUserName(username) == null) {
                    viewModel.createUsername(username, dateOfBirth)
                    jetNavController.navigateToEnterName()
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewCreateUsernameScreen() {
    CreateUsernameScreen(rememberJetNavController(), 1710277469758)
}

