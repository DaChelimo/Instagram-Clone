package com.da_chelimo.ig_clone.repo.user

import com.da_chelimo.ig_clone.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.first

class UserRepo {
    val fireStore = Firebase.firestore

    companion object{
        val USERS = "users"
        val DETAILS = "details"
    }


    suspend fun fetchUser(userID: String) =
        fireStore.collection(USERS)
            .document(userID)
            .dataObjects<User>()
            .first()


}