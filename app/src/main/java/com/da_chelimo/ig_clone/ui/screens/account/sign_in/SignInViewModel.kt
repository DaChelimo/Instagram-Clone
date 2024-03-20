package com.da_chelimo.ig_clone.ui.screens.account.sign_in

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SignInViewModel: ViewModel() {
    val auth = Firebase.auth

    suspend fun signInWithEmailAndPassword(email: String, passWord: String) {
        auth.signInWithEmailAndPassword(email, passWord)
            .await()
    }

}