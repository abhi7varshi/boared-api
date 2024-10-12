package com.example.boaredapi.data.local

import com.example.boaredapi.data.models.Post

fun Post.toEntity(isLiked: Boolean = false): PostEntity {
    return PostEntity(
        id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body,
        isLiked = isLiked
    )
}

fun PostEntity.toPost(): Post {
    return Post(
        id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body
    )
}