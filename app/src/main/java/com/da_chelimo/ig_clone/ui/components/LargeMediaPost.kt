package com.da_chelimo.ig_clone.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.da_chelimo.ig_clone.R
import com.da_chelimo.ig_clone.models.Comment
import com.da_chelimo.ig_clone.models.User
import com.da_chelimo.ig_clone.models.media.ImagePost
import com.da_chelimo.ig_clone.ui.theme.Grey
import com.da_chelimo.ig_clone.ui.theme.PoppinsFont
import com.da_chelimo.ig_clone.ui.theme.PreviewDarkTheme
import com.da_chelimo.ig_clone.utils.getDurationAgo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LargeImagePost(
    modifier: Modifier = Modifier,
    imagePost: ImagePost,
    currentUser: User?,
    uploadComment: (String) -> Unit,
    coroutineScope: CoroutineScope,
    fetchComments: suspend (String) -> List<Comment>
) {
    Column(modifier = modifier) {
        LargeImagePostHeader(modifier = Modifier.padding(vertical = 4.dp), imagePost = imagePost)

        GlideImage(
            model = imagePost.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )

        LargePostDetails(
            imagePost = imagePost,
            currentUser = currentUser,
            uploadComment = uploadComment,
            coroutineScope = coroutineScope,
            fetchComments = fetchComments
        )
    }
}


//@Preview
//@Composable
//fun PreviewLargeImagePost() {
//    PreviewDarkTheme {
//        LargeImagePost(
//            imagePost = ImagePost.TEST_POST,
//            currentUser = User.TEST_USER,
//            uploadComment = {},
//            coroutineScope = rememberCoroutineScope(),
//            fetchComments = { Comment.LIST_OF_TEST_COMMENTS }
//        )
//    }
//}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LargeImagePostHeader(modifier: Modifier = Modifier, imagePost: ImagePost) {
    Row(modifier = modifier) {
        CircularUserImage(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, bottom = 4.dp),
            userIcon = imagePost.userIcon,
            size = 32.dp
        )

        Text(
            text = imagePost.userName,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .align(Alignment.CenterVertically)
        )

        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Open more",
            modifier = Modifier
                .size(32.dp)
                .padding(5.dp)
        )
    }
}

//@Preview
//@Composable
//fun PreviewLargePostDetails() {
//    PreviewDarkTheme {
//        LargePostDetails(
//            imagePost = ImagePost.TEST_POST,
//            currentUser = User.TEST_USER,
//            uploadComment = {},
//            coroutineScope = rememberCoroutineScope(),
//            fetchComments = { Comment.LIST_OF_TEST_COMMENTS }
//        )
//    }
//}

@Composable
fun LargePostDetails(
    modifier: Modifier = Modifier,
    imagePost: ImagePost,
    currentUser: User?,
    uploadComment: (String) -> Unit,
    coroutineScope: CoroutineScope,
    fetchComments: suspend (String) -> List<Comment>
) {
    Column(modifier = modifier.padding(start = 8.dp, end = 8.dp)) {
        LargeMediaPostOptions(imagePost = imagePost)

        LargePostMediaLikedBy(modifier = Modifier.padding(top = 6.dp), imagePost = imagePost)

        LargePostMediaComment(
            modifier = Modifier.padding(top = 4.dp),
            imagePost = imagePost,
            currentUser = currentUser,
            uploadComment = uploadComment,
            coroutineScope = coroutineScope,
            fetchComments = fetchComments
        )

        Text(
            imagePost.dateUploaded.getDurationAgo(),
            color = Grey,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 6.dp, bottom = 8.dp)
        )
    }
}

@Composable
fun LargeMediaPostOptions(modifier: Modifier = Modifier, imagePost: ImagePost) {
    Row(modifier = modifier.fillMaxWidth()) {
        // Change this based on whether the user has liked it
        Icon(
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = "Like Post",
            modifier = Modifier
                .padding(end = 2.dp)
                .size(36.dp)
                .padding(end = 4.dp, bottom = 4.dp, top = 4.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Icon(
            painter = painterResource(id = R.drawable.rounded_mode_comment_24),
            contentDescription = "Add Comment",
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .size(36.dp)
                .padding(horizontal = 4.dp, vertical = 4.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Icon(
            imageVector = Icons.Outlined.Send,
            contentDescription = "Share Post",
            modifier = Modifier
                .rotate(-45f)
                .padding(start = 6.dp)
                .size(34.dp)
                .padding(start = 4.dp, bottom = 4.dp, top = 4.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.weight(1f))

        // Change this to Saved Icon and reactive to save or not saved status
        Icon(
            imageVector = Icons.Outlined.Email,
            contentDescription = "Save Post",
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .size(40.dp)
                .padding(horizontal = 4.dp, vertical = 4.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

//@Preview
//@Composable
//fun PreviewLargeMediaPostOptions() {
//    PreviewDarkTheme {
//        LargeMediaPostOptions(imagePost = ImagePost.TEST_POST)
//    }
//}


// Automate how one liker is chosen from the like list
@Composable
fun LargePostMediaLikedBy(modifier: Modifier = Modifier, imagePost: ImagePost) {
//    Text(text = buildAnnotatedString {
//        append("Liked by ")
//        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
//        append("the_chelimo")
//        pushStyle(SpanStyle(fontWeight = FontWeight.Normal))
//        append(" and ")
//        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
//        append("${imagePost.likeCount - 1} others")
//    })

    // OR

    Text(
        modifier = modifier,
        text = "${imagePost.likeCount} likes",
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    )
}


//@Preview
//@Composable
//fun PreviewLargePostMediaComment() {
//    PreviewDarkTheme {
//        LargePostMediaComment(
//            imagePost = ImagePost.TEST_POST,
//            currentUser = User.TEST_USER,
//            coroutineScope = rememberCoroutineScope(),
//            uploadComment = {},
//            fetchComments = { Comment.LIST_OF_TEST_COMMENTS }
//        )
//    }
//}

@Composable
fun LargePostMediaComment(
    modifier: Modifier = Modifier,
    imagePost: ImagePost,
    currentUser: User?,
    uploadComment: (String) -> Unit,
    coroutineScope: CoroutineScope,
    fetchComments: suspend (String) -> List<Comment>
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row {
            Text(
                imagePost.userName,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                modifier = Modifier.padding(end = 4.dp)
            )

            var isExpanded by remember { mutableStateOf(false) }
            Text(
                text = imagePost.caption,
                fontSize = 13.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                modifier = Modifier.clickable {
                    isExpanded = !isExpanded
                }
            )
        }


        var showComments by remember { mutableStateOf(false) }

        if (imagePost.commentCount > 0) {
            Text(
                "View all ${imagePost.commentCount} comments",
                color = Grey,
                fontSize = 13.sp,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable { showComments = true }
            )
        }

        var showWriteCommentTab by remember { mutableStateOf(false) }
        if (currentUser != null) {
            Row(modifier = Modifier
                .padding(top = 4.dp)
                .clickable { showWriteCommentTab = true }) {
                CircularUserImage(
                    userIcon = currentUser.userIcon, size = 32.dp,
                    modifier = Modifier
                        .padding(end = 8.dp)
                )

                Text(
                    "Add a comment...",
                    color = Grey,
                    fontSize = 13.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

        if (showWriteCommentTab) {
            WriteCommentPopup(
                userIcon = currentUser?.userIcon,
                uploadComment = uploadComment
            )
        }


        if (showComments) {
            CommentsBottomSheet(coroutineScope, imagePost, fetchComments)
        }
    }
}

// TODO: Continue LATER!!!!
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    coroutineScope: CoroutineScope,
    imagePost: ImagePost,
    fetchComments: suspend (String) -> List<Comment>
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { coroutineScope.launch { sheetState.hide() } },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {

        Text("Comments", fontFamily = PoppinsFont, fontSize = 14.sp, fontWeight = FontWeight.Medium)

        Divider(
            modifier = Modifier
                .padding(top = 6.dp, bottom = 6.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(Grey)
        )

        LazyColumn {
            coroutineScope.launch {
                val comments = fetchComments(imagePost.id)

                items(comments) { comment ->
                    PostComment(comment = comment)
                }
            }
        }
    }

    DisposableEffect(Unit) {
        coroutineScope.launch { sheetState.expand() }
        onDispose {  }
    }
}


//@Preview
//@Composable
//fun PreviewLargePostCommentsBottomSheet() {
//    CommentsBottomSheet(
//        coroutineScope = rememberCoroutineScope(),
//        imagePost = ImagePost.TEST_POST,
//        fetchComments = { Comment.LIST_OF_TEST_COMMENTS })
//}

@Composable
fun WriteCommentPopup(
    userIcon: String?,
    uploadComment: (String) -> Unit
) {
    val commentMessage = remember {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }


    Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)) {
        CircularUserImage(
            userIcon = userIcon,
            size = 32.dp,
            modifier = Modifier.padding(end = 8.dp)
        )
        BasicTextField(
            value = commentMessage.value,
            onValueChange = { commentMessage.value = it },
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            singleLine = true,
            keyboardActions = KeyboardActions(onSend = {
                uploadComment(commentMessage.value)
            })
        )
        DisposableEffect(Unit) {
            focusRequester.requestFocus()
            onDispose {}
        }

        if (commentMessage.value.isNotBlank()) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Upload comment",
                tint = Color.White,
                modifier = Modifier
                    .size(60.dp, height = 40.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(Color.Blue)
                    .clickable { uploadComment(commentMessage.value) }
                    .padding(horizontal = 20.dp, vertical = 5.dp)
            )
        }
    }

}
