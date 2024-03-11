package com.da_chelimo.ig_clone.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.da_chelimo.ig_clone.R
import com.da_chelimo.ig_clone.ui.theme.Grey

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CircularUserImage(modifier: Modifier = Modifier, userIcon: String?, size: Dp) {
    if (userIcon == null) {
        Image(
            painter = painterResource(id = R.drawable.me), // Icons.Filled.Person,
            null,
            modifier = modifier
                .clip(CircleShape)
                .size(size)
                .background(Grey),
            colorFilter = ColorFilter.tint(Color.White)
        )
    } else {
        GlideImage(
            userIcon,
            null,
            modifier = modifier.clip(CircleShape).size(size),
            contentScale = ContentScale.Crop
        )
    }

}