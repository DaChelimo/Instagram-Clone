package com.da_chelimo.ig_clone.models

data class User(
    val uid: String,
    var username: String,
    var fullName: String,
    var userIcon: String?,
    val dateOfBirth: Long,
    var bio: String,
    var posts: Int,
    var followers: Int,
    var following: Int
) {

    companion object {
        val TEST_USER = User(
            uid = "123abc",
            username = "mini_chelimo",
            fullName = "Mini Chelimo",
            userIcon = null,
            dateOfBirth = 1709885460731,
            bio = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sed rhoncus lectus. Proin id est sit amet ipsum ultrices bibendum sollicitudin sit amet ligula. Ut laoreet ullamcorper mauris", //"In the lowlands, may we blossom",
            posts = 16,
            followers = 80,
            following = 68
        )
    }

}
