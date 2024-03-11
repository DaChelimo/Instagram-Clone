package com.da_chelimo.ig_clone.ui.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.da_chelimo.ig_clone.models.User
import com.da_chelimo.ig_clone.models.media.ImagePost
import com.da_chelimo.ig_clone.models.state.FetchState
import com.da_chelimo.ig_clone.repo.media.MediaRepo
import com.da_chelimo.ig_clone.repo.user.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val mediaRepo: MediaRepo
): ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _listOfImagePost = MutableLiveData<List<ImagePost>>(listOf())
    val listOfImagePost: LiveData<List<ImagePost>> = _listOfImagePost

    private val _fetchState = MutableLiveData(FetchState.LOADING)
    val fetchState: LiveData<FetchState> = _fetchState

    fun loadUser(userID: String) {
        viewModelScope.launch {
            _user.value = userRepo.fetchUser(userID)
            _fetchState.value = FetchState.DONE

            loadUserImages(userID)
        }
    }

    private suspend fun loadUserImages(userID: String) {
        _listOfImagePost.value = mediaRepo.getUserImages(userID)
    }

}