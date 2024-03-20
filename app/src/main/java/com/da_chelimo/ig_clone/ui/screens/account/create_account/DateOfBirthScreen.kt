package com.da_chelimo.ig_clone.ui.screens.account.create_account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.da_chelimo.ig_clone.models.OldScreens
import com.da_chelimo.ig_clone.navigation.JetNavController
import com.da_chelimo.ig_clone.navigation.rememberJetNavController
import com.da_chelimo.ig_clone.ui.components.sign_in.CreateAccountHeader
import com.da_chelimo.ig_clone.ui.components.sign_in.SignInButton
import com.da_chelimo.ig_clone.ui.components.sign_in.getSignInTextFieldColors
import com.da_chelimo.ig_clone.ui.theme.BrightBlue
import com.da_chelimo.ig_clone.ui.theme.DateOfBirthDialogColor
import com.da_chelimo.ig_clone.ui.theme.SignInBlue
import com.da_chelimo.ig_clone.utils.getFormattedDateOfBirth
import com.da_chelimo.ig_clone.utils.getYearsOld
import com.da_chelimo.ig_clone.utils.is3YearsAndBelow
import org.joda.time.DateTime
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateOfBirthScreen(jetNavController: JetNavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SignInBlue)
            .padding(horizontal = 8.dp)
    ) {
        var shouldOpenDateDialog by remember {
            mutableStateOf(true)
        }

        CreateAccountHeader(
            modifier = Modifier,
            mainTitle = "What's your date of birth?",
            description = "Use your own date of birth, even if this account is for a business, a pet or something else. No one will see this unless you choose to share it.",
            jetNavController = jetNavController,
            backBtnContentDescription = "Go Back Button"
        )

        var dateOfBirth by remember {
            mutableLongStateOf(DateTime.now().millis)
        }
        val formattedDateOfBirth = dateOfBirth.getFormattedDateOfBirth()

        OutlinedTextField(
            label = { Text(text = "Date Of Birth (${dateOfBirth.getYearsOld()})") },
            value = formattedDateOfBirth,
            colors = getSignInTextFieldColors(),
            onValueChange = {},
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .clickable {
                    shouldOpenDateDialog = true
                    Timber.d("DOB clickable called with shouldOpenDateDialog as $shouldOpenDateDialog")
                },
            isError = dateOfBirth.is3YearsAndBelow(),
            supportingText = {
                if (dateOfBirth.is3YearsAndBelow())
                    Text(text = "You should be 4 years or older to create an account")
            },
            readOnly = true
        )

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = DateTime.now().millis,
            yearRange = IntRange(1900, DateTime.now().year)
        )

        if (shouldOpenDateDialog) {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    surface = DateOfBirthDialogColor,
                    onSurface = Color.White,
                )
            ) {
                DatePickerDialog(
                    onDismissRequest = { shouldOpenDateDialog = false },
                    colors = DatePickerDefaults.colors(
                        selectedDayContainerColor = Color.White,
                        selectedDayContentColor = SignInBlue,
                        todayDateBorderColor = Color.White
                    ),
                    confirmButton = {
                        TextButton(onClick = {
                            dateOfBirth =
                                datePickerState.selectedDateMillis ?: DateTime.now().millis
                            shouldOpenDateDialog = false
                        }) {
                            Text(text = "Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            shouldOpenDateDialog = false
                        }) {
                            Text(text = "Cancel")
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState,
                        colors = DatePickerDefaults.colors(
                            selectedDayContainerColor = Color.White,
                            selectedDayContentColor = SignInBlue,
                            todayDateBorderColor = Color.White,
                            containerColor = DateOfBirthDialogColor,
                            currentYearContentColor = Color.White
                        )
                    )
                }
            }
        }


        SignInButton(
            modifier = Modifier.padding(top = 16.dp),
            buttonText = "Next",
            textColor = Color.White,
            containerColor = BrightBlue,
            outlineColor = Color.Transparent,
            onClick = {
                jetNavController.navigateToCreateUsername(dateOfBirth)
            }
        )
    }
}

@Preview
@Composable
fun PreviewDateOfBirthScreen() {
    DateOfBirthScreen(rememberJetNavController())
}