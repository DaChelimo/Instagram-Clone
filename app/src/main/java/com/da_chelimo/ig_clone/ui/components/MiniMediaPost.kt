package com.da_chelimo.ig_clone.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.da_chelimo.ig_clone.R
import com.da_chelimo.ig_clone.models.media.ImagePost
import com.da_chelimo.ig_clone.models.media.SimpleImage


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MiniImagePost(imagePost: ImagePost, openPost: (ImagePost) -> Unit) {
//    val drawable = CircularProgressDrawable(LocalContext.current)
//    val primaryColor = MaterialTheme.colorScheme.primary
//    val progressColor = MaterialTheme.colorScheme.onPrimary
//    drawable.setColorSchemeColors(primaryColor.value, progressColor)
//    drawable.setCenterRadius(30f)
//    drawable.setStrokeWidth(5f)
//    // set all other properties as you would see fit and start it
//    // set all other properties as you would see fit and start it
//    drawable.start()
    GlideImage(
        model = imagePost.imageUrl,
        contentDescription = null, contentScale = ContentScale.Crop,
//        loading = placeholder(drawable), failure = placeholder(),
        loading = placeholder(R.drawable.loading_food),
        failure = placeholder(R.drawable.loading_food),
        modifier = Modifier
            .fillMaxWidth()
//            .height(120.dp)
            .clickable { openPost(imagePost) }
    )
}

