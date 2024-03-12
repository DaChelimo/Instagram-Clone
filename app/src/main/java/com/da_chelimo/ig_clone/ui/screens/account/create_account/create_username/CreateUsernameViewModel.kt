package com.da_chelimo.ig_clone.ui.screens.account.create_account.create_username

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.da_chelimo.ig_clone.models.User
import com.da_chelimo.ig_clone.repo.user.UserRepo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class CreateUsernameViewModel : ViewModel() {
    private val userRepo = UserRepo()

    fun createUsername(username: String, dateOfBirth: Long) {
        val user = User(
            uid = Firebase.auth.uid!!,
            username = username,
            fullName = username,
            userIcon = null,
            dateOfBirth = dateOfBirth,
            bio = "",
            posts = 0,
            followers = 0,
            following = 0
        )

        viewModelScope.launch {
            userRepo.createUser(user)
        }
    }

}