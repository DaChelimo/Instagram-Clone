package com.da_chelimo.ig_clone.ui.screens.post

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.da_chelimo.ig_clone.models.state.FetchState
import com.da_chelimo.ig_clone.repo.media.MediaRepo
import kotlinx.coroutines.launch

class CreatePostViewModel: ViewModel() {
    private val mediaRepo = MediaRepo()

    private val _uploadState = MutableLiveData<FetchState?>(null)
    val uploadState: LiveData<FetchState?> = _uploadState

    fun sharePost(image: Uri, caption: String) {
        _uploadState.value = FetchState.LOADING
        viewModelScope.launch {
            mediaRepo.postImage(image, caption)
        }.invokeOnCompletion {
            _uploadState.value =
                if (it == null) FetchState.DONE else FetchState.FAILED
        }
    }

    fun resetUploadState() {
        _uploadState.value = null
    }

}