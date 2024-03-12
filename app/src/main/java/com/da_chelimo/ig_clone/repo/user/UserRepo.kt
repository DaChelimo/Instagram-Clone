package com.da_chelimo.ig_clone.repo.user

import com.da_chelimo.ig_clone.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

class UserRepo {
    val fireStore = Firebase.firestore

    companion object {
        val USERS = "users"
        val DETAILS = "details"
    }

    suspend fun createUser(user: User) =
        fireStore.collection(USERS)
            .document(user.uid)
            .set(user)
            .await()

    suspend fun updateFullName(newFullName: String) =
        fireStore.collection(USERS)
            .document(Firebase.auth.uid!!)
            .update("fullName", newFullName)
            .await()

    suspend fun updateProfilePic() {
        // TODO: Upload image and store image url to DB

        fireStore.collection(USERS)
            .document(Firebase.auth.uid!!)
            .update("userIcon", "")
            .await()
    }

    suspend fun fetchUser(userID: String) =
        fireStore.collection(USERS)
            .document(userID)
            .dataObjects<User>()
            .first()


}