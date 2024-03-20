package com.da_chelimo.ig_clone.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.da_chelimo.ig_clone.models.User
import com.da_chelimo.ig_clone.models.media.ImagePost
import com.da_chelimo.ig_clone.repo.media.MediaRepo
import com.da_chelimo.ig_clone.repo.user.UserRepo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val mediaRepo = MediaRepo()
    private val userRepo = UserRepo()

    private val _posts = MutableLiveData<List<ImagePost>>(listOf())
    val posts: LiveData<List<ImagePost>> = _posts

    var currentUser: User? = null
        private set

    init {
        viewModelScope.launch {
            _posts.value = mediaRepo.getPosts()

            if (Firebase.auth.currentUser != null) {
                currentUser = userRepo.getCurrentUser()
            }
        }
    }

}