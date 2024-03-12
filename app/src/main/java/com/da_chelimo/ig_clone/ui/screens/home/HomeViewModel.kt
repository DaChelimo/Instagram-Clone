package com.da_chelimo.ig_clone.ui.screens.home

import androidx.lifecycle.ViewModel
import com.da_chelimo.ig_clone.repo.media.MediaRepo

class HomeViewModel: ViewModel() {
    val mediaRepo = MediaRepo()

    fun fetchPost() {
        mediaRepo
    }

}