package com.da_chelimo.ig_clone.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.da_chelimo.ig_clone.R
import com.da_chelimo.ig_clone.models.media.ImagePost


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MiniImagePost(imagePost: ImagePost, openPost: (ImagePost) -> Unit) {
    GlideImage(
        model = imagePost.imageUrl,
        contentDescription = null, contentScale = ContentScale.Crop,
        loading = placeholder {
            Column(
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(36.dp),
                    strokeWidth = 1.dp,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        },
        failure = placeholder {
            Image(
                painter = painterResource(id = R.drawable.loading_food),
                contentDescription = null
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { openPost(imagePost) }
    )
}

