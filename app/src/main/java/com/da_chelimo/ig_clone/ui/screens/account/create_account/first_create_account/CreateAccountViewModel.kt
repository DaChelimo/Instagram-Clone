package com.da_chelimo.ig_clone.ui.screens.account.create_account.first_create_account

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CreateAccountViewModel : ViewModel() {

    val auth = Firebase.auth
    var verificationId: String? = null

    fun signInWithNumber(number: String, activity: Activity) {
        val phoneAuthOptions = PhoneAuthOptions.Builder(Firebase.auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout for the code sent via SMS
            .setActivity(activity) // The activity to which the user is navigated to enter the code
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(authCredential: PhoneAuthCredential) {
                    Timber.d("authCredential is ${authCredential.smsCode}")
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Timber.e(p0)
                }

                override fun onCodeSent(id: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(id, forceResendingToken)
                    // The verification ID obtained in onCodeSent()
                    verificationId = id
                }
            }) // Implement PhoneAuthProvider.OnVerificationStateChangedCallbacks
            .build()

        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
    }


    suspend fun verifyConfirmationCode(code: String): AuthResult? {
        Timber.d("verificationId is $verificationId")
        if (verificationId == null) return null

        // To verify the entered code
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)

        return auth.signInWithCredential(credential).await()
    }
}