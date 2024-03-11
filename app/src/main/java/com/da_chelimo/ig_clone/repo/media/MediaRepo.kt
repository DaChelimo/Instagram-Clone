package com.da_chelimo.ig_clone.repo.media

import com.da_chelimo.ig_clone.models.media.ImagePost
import com.da_chelimo.ig_clone.models.media.SimpleImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first

class MediaRepo {

    val fireStore = Firebase.firestore
    val fireAuth = Firebase.auth

    companion object {
        val MEDIA = "media"
        val IMAGE_POSTS = "image_posts"
        val VIDEO_POSTS = "video_posts"
    }

    val currentUserImages: Flow<List<ImagePost>?>
        get() = fireAuth.currentUser?.uid?.let {  currentUid ->
            fireStore
                .collection(MEDIA)
                .document(currentUid)
                .collection(IMAGE_POSTS)
                .dataObjects()
        } ?: emptyFlow()

    suspend fun getUserImages(userID: String) =
        fireStore
            .collection(MEDIA)
            .document(userID)
            .collection(IMAGE_POSTS)
            .dataObjects<ImagePost>()
            .first()

}