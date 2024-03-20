package com.da_chelimo.ig_clone.repo.media

import android.net.Uri
import com.da_chelimo.ig_clone.models.media.ImagePost
import com.da_chelimo.ig_clone.repo.user.UserRepo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import org.joda.time.DateTime
import java.util.UUID

class MediaRepo {

    val userRepo = UserRepo()

    val fireStore = Firebase.firestore
    val fireAuth = Firebase.auth

    companion object {
        val MEDIA = "media"
        val IMAGE_POSTS = "image_posts"
        val VIDEO_POSTS = "video_posts"

        val POSTS = "posts"
    }

    suspend fun getPosts() = fireStore
        .collection(POSTS)
        .orderBy("likeCount")
        .limit(10)
        .dataObjects<ImagePost>()
        .first()

    suspend fun postImage(uri: Uri, caption: String) {
        val imageID = UUID.randomUUID().toString()
        val uploadTask = Firebase.storage
            .getReference("images/${Firebase.auth.uid}/$imageID")
            .putFile(uri)
            .await()

        val downloadUrl = uploadTask.storage.downloadUrl.await().toString()

        val user = userRepo.getCurrentUser()

        val imagePost = ImagePost(
            imageID,
            caption,
            downloadUrl,
            DateTime().millis,
            user?.userIcon,
            user!!.username,
            0,
            0
        )


        fireStore.runTransaction {
            fireStore
                .collection(MEDIA)
                .document(Firebase.auth.uid!!)
                .collection(IMAGE_POSTS)
                .add(imagePost)

            fireStore
                .collection(POSTS)
                .add(imagePost)
        }.await()
    }

    suspend fun getUserImages(userID: String) =
        fireStore
            .collection(MEDIA)
            .document(userID)
            .collection(IMAGE_POSTS)
            .dataObjects<ImagePost>()
            .first()

}