package com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class CreateAccountViewModel : ViewModel() {

    fun signInWithNumber(number: String, activity: Activity) {
        PhoneAuthOptions.Builder(Firebase.auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout for the code sent via SMS
            .setActivity(activity) // The activity to which the user is navigated to enter the code
//            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
//
//                }
//
//                override fun onVerificationFailed(p0: FirebaseException) {
//                    Timber.e(p0)
//                }
//            }            }) // Implement PhoneAuthProvider.OnVerificationStateChangedCallbacks
//            .build()
    }

}