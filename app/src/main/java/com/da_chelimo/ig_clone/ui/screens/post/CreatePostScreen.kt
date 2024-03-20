package com.da_chelimo.ig_clone.ui.screens.post

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.da_chelimo.ig_clone.models.OldScreens
import com.da_chelimo.ig_clone.models.state.FetchState
import com.da_chelimo.ig_clone.navigation.JetNavController
import com.da_chelimo.ig_clone.navigation.Screens
import com.da_chelimo.ig_clone.navigation.rememberJetNavController
import com.da_chelimo.ig_clone.ui.theme.Black
import com.da_chelimo.ig_clone.ui.theme.BrightBlue
import com.da_chelimo.ig_clone.ui.theme.Grey
import com.da_chelimo.ig_clone.ui.theme.LightWhite
import com.da_chelimo.ig_clone.ui.theme.PoppinsFont

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CreatePostScreen(
    image: Uri,
    jetNavController: JetNavController
) {
    val viewModel = viewModel<CreatePostViewModel>()
    val uploadState by viewModel.uploadState.observeAsState()

    val snackbarHostState = SnackbarHostState()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { jetNavController.upPress() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Go back",
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .size(52.dp)
                    )
                }

                Text(
                    text = "New Post",
                    modifier = Modifier.padding(start = 8.dp),
                    letterSpacing = (0.5).sp
                )
            }

            when (uploadState) {
                FetchState.LOADING -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp), color = BrightBlue, strokeWidth = 2.dp
                        )

                    }
                }

                FetchState.FAILED -> {
                    LaunchedEffect(Unit) {
                        snackbarHostState.showSnackbar("Error occurred during upload")
                    }
                }

                FetchState.DONE -> {
                    LaunchedEffect(Unit) {
                        jetNavController.navigateToBottomBarRoute(Screens.HOME)
                        viewModel.resetUploadState()
                    }
                }

                null -> {
                    GlideImage(
                        model = image,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .height(250.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillHeight
                    )


                    var postCaption by remember {
                        mutableStateOf("")
                    }

                    TextField(
                        value = postCaption,
                        onValueChange = { postCaption = it },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Black,
                            unfocusedContainerColor = Black,
                            focusedPlaceholderColor = LightWhite,
                            unfocusedPlaceholderColor = LightWhite,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(fontSize = 14.sp),
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .padding(top = 12.dp)
                            .fillMaxWidth(),
                        placeholder = { Text("Write a caption or add a poll...", fontSize = 14.sp) }
                    )

                    Divider(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .height(1.dp)
                            .background(Grey)
                    )

                    TextButton(
                        onClick = {
                            viewModel.sharePost(image, postCaption)
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = BrightBlue
                        ),
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .padding(vertical = 24.dp, horizontal = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Share",
                            fontFamily = PoppinsFont,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewCreatePostScreen() {
    CreatePostScreen(image = Uri.EMPTY, jetNavController = rememberJetNavController())
}

