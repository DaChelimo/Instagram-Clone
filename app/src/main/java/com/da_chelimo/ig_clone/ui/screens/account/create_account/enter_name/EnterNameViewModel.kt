package com.da_chelimo.ig_clone.ui.screens.account.create_account.enter_name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.da_chelimo.ig_clone.repo.user.UserRepo
import kotlinx.coroutines.launch

class EnterNameViewModel: ViewModel() {
    private val userRepo = UserRepo()

    fun enterName(fullName: String) {
        viewModelScope.launch {
            userRepo.updateFullName(fullName)
        }
    }

}