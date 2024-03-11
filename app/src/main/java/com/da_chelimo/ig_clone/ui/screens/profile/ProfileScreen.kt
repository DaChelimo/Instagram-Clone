package com.da_chelimo.ig_clone.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.da_chelimo.ig_clone.R
import com.da_chelimo.ig_clone.models.User
import com.da_chelimo.ig_clone.models.media.ImagePost
import com.da_chelimo.ig_clone.models.state.FetchState
import com.da_chelimo.ig_clone.ui.components.CircularUserImage
import com.da_chelimo.ig_clone.ui.components.MiniImagePost
import com.da_chelimo.ig_clone.ui.theme.PoppinsFont
import com.da_chelimo.ig_clone.ui.theme.PreviewDarkTheme
import com.da_chelimo.ig_clone.utils.toFormattedNumWithSymbols
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(userID: String, navController: NavController) {
    val viewModel = viewModel<ProfileViewModel>()
    viewModel.loadUser(userID)


    val user by viewModel.user.observeAsState()
    val fetchState by viewModel.fetchState.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        ProfileTopBar(
            modifier = Modifier.fillMaxWidth(),
            username = user?.username,
            openAccountsBottomDrawer = {},
            isMyAccount = userID == Firebase.auth.currentUser?.uid,
            onNavigateBackIfOthersProfile = { navController.popBackStack() },
            createPost = {},
            openMenu = {}
        )

        if (fetchState == FetchState.LOADING) {
            Column(verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        else if (fetchState == FetchState.DONE && user != null) {
            ProfileHeader(
                user = user,
                editProfile = { /*TODO*/ },
                shareProfile = {}
            )

            ProfileMedia(listOfImagePost = viewModel.listOfImagePost, openPost = {})
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    modifier = Modifier.size(120.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "No network available", fontSize = 17.sp, fontFamily = PoppinsFont)
            }
        }
    }
}


@Composable
fun ProfileTopBar(
    modifier: Modifier = Modifier,
    username: String?,
    isMyAccount: Boolean,
    onNavigateBackIfOthersProfile: () -> Unit,
    openAccountsBottomDrawer: () -> Unit,
    createPost: () -> Unit,
    openMenu: () -> Unit
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier
            .weight(1f)
            .clickable { openAccountsBottomDrawer() }) {

            if (!isMyAccount) {
                Icon(
                    Icons.Default.ArrowBack,
                    "Back Button",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(horizontal = 8.dp)
                        .clickable {
                            onNavigateBackIfOthersProfile()
                        }
                )
            }

            Text(
                text = username ?: "",
                fontFamily = PoppinsFont,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                modifier = Modifier.padding(start = 8.dp)
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "Open Accounts Drawer",
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
            )
        }

        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = "Create Post",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(30.dp)
                .clickable { createPost() }
                .size(20.dp)
        )

        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Open Menu",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(8.dp)
                .size(30.dp)
                .clickable { openMenu() }
                .size(20.dp)
        )
    }
}

//@Preview
@Composable
fun PreviewProfileTopBar() {
    PreviewDarkTheme {
        ProfileTopBar(
            username = "mini_chelimo",
            isMyAccount = false,
            onNavigateBackIfOthersProfile = {},
            openAccountsBottomDrawer = {},
            createPost = {},
            openMenu = {})
    }
}


@Composable
fun ProfileHeader(user: User?, editProfile: () -> Unit, shareProfile: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row {
            CircularUserImage(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clip(CircleShape),
                size = 76.dp,
                userIcon = null
            )
            Spacer(modifier = Modifier.width(24.dp))

            ProfileStats(
                modifier = Modifier
                    .height(76.dp)
                    .align(Alignment.CenterVertically),
                postsCount = user?.posts,
                followerCount = user?.followers,
                followingCount = user?.following
            )
        }
        Text(
            text = user?.name ?: "",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 4.dp, start = 8.dp)
        )
        Text(
            text = user?.bio ?: "",
            modifier = Modifier.padding(top = 2.dp, start = 8.dp),
            maxLines = 5,
            fontSize = 12.sp,
            fontFamily = PoppinsFont,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .fillMaxWidth()
        ) {
            val roundedCornerShape = RoundedCornerShape(6.dp)

            Button(
                onClick = { editProfile() }, modifier = Modifier
                    .heightIn(max = 36.dp)
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.surface, roundedCornerShape)
                    .padding(top = 0.dp, bottom = 0.dp, end = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    text = "Edit Profile"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { shareProfile() },
                modifier = Modifier
                    .heightIn(max = 36.dp)
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.surface, roundedCornerShape)
                    .padding(top = 0.dp, bottom = 0.dp, end = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    text = "Share Profile"
                )
            }
        }

        ProfileHighlights()
    }
}

//@Preview
//@Composable
//fun PreviewProfileHeader() {
//    PreviewDarkTheme {
//        ProfileHeader(
//            user = User(
//                username = "mini_chelimo",
//                name = "Mini Chelimo",
//                bio = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sed rhoncus lectus. Proin id est sit amet ipsum ultrices bibendum sollicitudin sit amet ligula. Ut laoreet ullamcorper mauris", //"In the lowlands, may we blossom",
//                posts = 16,
//                followers = 80,
//                following = 68
//            ), editProfile = {}, shareProfile = {})
//    }
//}


@Composable
fun ProfileHighlights() {
    // TODO: Replace this with actual type of highlights
    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)) {
        Icon(
            Icons.Outlined.Add, "Add new highlight", modifier = Modifier
                .clip(CircleShape)
                .size(60.dp)
                .border(1.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
                .padding(12.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = "New",
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun ProfileStats(
    modifier: Modifier = Modifier,
    postsCount: Int?,
    followerCount: Int?,
    followingCount: Int?
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileStat(count = postsCount, name = "posts")
        ProfileStat(count = followerCount, name = "followers")
        ProfileStat(count = followingCount, name = "following")
    }
}

//@Preview
//@Composable
//fun PreviewProfileStats() {
//    PreviewDarkTheme {
//        ProfileStats(postsCount = 20, followerCount = 11_200, followingCount = 120)
//    }
//}

@Composable
fun ProfileStat(count: Int?, name: String) {
    Column {
        Text(
            text = count?.toFormattedNumWithSymbols() ?: "0",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = name, fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}


@Composable
fun ProfileMedia(listOfImagePost: LiveData<List<ImagePost>>, openPost: (ImagePost) -> Unit) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                if (selectedTabIndex < tabPositions.size) {
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .height(3.dp)
                            .background(
                                MaterialTheme.colorScheme.onPrimary,
                                RoundedCornerShape(60.dp)
                            )
                    )
                }
            }
        ) {
            Tab(selected = selectedTabIndex == 0, modifier = Modifier, onClick = {
                selectedTabIndex = 0
            }) {
                Icon(
                    painterResource(id = R.drawable.ic_grid),
                    "View photos",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }

            Tab(selected = selectedTabIndex == 1, onClick = {
                selectedTabIndex = 1
            }) {
                Icon(
                    painterResource(id = R.drawable.rounded_video),
                    "View reels",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }

        // Show images
        if (selectedTabIndex == 0) {
            ImagesGrid(listOfImagePost, openPost)
        } else {
            ReelsGrid()
        }
    }
}

@Preview
@Composable
fun PreviewProfileMedia() {
    PreviewDarkTheme {
        ProfileMedia(MutableLiveData(ImagePost.LIST_OF_TEST_POST), {})
    }
}

@Composable
fun ImagesGrid(listOfSimpleImage: LiveData<List<ImagePost>>, openPost: (ImagePost) -> Unit) {
    val imagePosts by listOfSimpleImage.observeAsState(initial = listOf<ImagePost>())

    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(imagePosts) { imagePost ->
            MiniImagePost(imagePost = imagePost, openPost = openPost)
        }
    }
}

@Preview
@Composable
fun PreviewImagesGrid() {
    ImagesGrid(
        listOfSimpleImage = MutableLiveData(ImagePost.LIST_OF_TEST_POST),
        openPost = {}
    )
}

@Composable
fun ReelsGrid() {

}
