package com.da_chelimo.ig_clone.ui.screens.post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import timber.log.Timber

@Composable
fun RequestForPostScreen(onImageSelected: (Uri) -> Unit, onImageRequestFailed: () -> Unit) {
//    ActivityResultContracts.PickVisualMedia()
    val singleImagePickerRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(), // ActivityResultContracts.PickVisualMedia(),
        onResult = { selectedImage ->
            Timber.d("selectedImage is $selectedImage")

            if (selectedImage != null)
                onImageSelected(selectedImage)
            else
                onImageRequestFailed()
        }
    )

    LaunchedEffect(Unit) {
        singleImagePickerRequest.launch("image/*") // (PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}

