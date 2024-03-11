package com.da_chelimo.ig_clone.models

data class Comment(
    val userId: String,
    val userIcon: String?,
    val userName: String,
    val commentText: String,
    val commentDateInMillis: Long,
    var commentLikeCount: Int,
    var replyCount: Int
) {

    companion object {
        val TEST_COMMENT =
            Comment(
                userId = "user1234",
                userIcon = null,
                userName = "the_chelimo",
                commentText = "Crazy work here!! Cheers mate :)",
                commentDateInMillis = 1710069436423,
                commentLikeCount = 4,
                replyCount = 40
            )

        val LIST_OF_TEST_COMMENTS = listOf(
            TEST_COMMENT,
            TEST_COMMENT,
            TEST_COMMENT,
            TEST_COMMENT,
            TEST_COMMENT,
            TEST_COMMENT,
            TEST_COMMENT
        )
    }

}
