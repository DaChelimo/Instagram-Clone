package com.da_chelimo.ig_clone.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.da_chelimo.ig_clone.models.Comment
import com.da_chelimo.ig_clone.ui.theme.Grey
import com.da_chelimo.ig_clone.ui.theme.PreviewDarkTheme
import com.da_chelimo.ig_clone.utils.getCommentTime
import com.da_chelimo.ig_clone.utils.getDurationAgo

@Composable
fun PostComment(modifier: Modifier = Modifier, comment: Comment) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)) {
        CircularUserImage(
            userIcon = comment.userIcon,
            size = 40.dp,
            modifier = Modifier.padding(start = 12.dp, end = 8.dp, top = 8.dp)
        )

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Row {
                Text(comment.userName, fontSize = 12.sp, fontWeight = FontWeight.Medium)

                Text(
                    comment.commentDateInMillis.getCommentTime(),
                    fontSize = 12.sp,
                    color = Grey,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }

            Text(
                text = comment.commentText, modifier = Modifier
                    .padding(top = 4.dp, end = 4.dp), fontSize = 13.sp
            )

            Text(
                text = "Reply",
                color = Grey, fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable {
                        // TODO: Open Reply Compose
                    }
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Like comment",
                modifier = Modifier
                    .padding(top = 6.dp, start = 6.dp, end = 6.dp, bottom = 0.dp),
                tint = Grey
            )

            Text(
                text = comment.commentLikeCount.toString(),
                fontSize = 11.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Preview
@Composable
fun PreviewPostComment() {
    PreviewDarkTheme {
        LazyColumn {
            items(Comment.LIST_OF_TEST_COMMENTS) {
                PostComment(comment = it)
            }
        }
    }
}