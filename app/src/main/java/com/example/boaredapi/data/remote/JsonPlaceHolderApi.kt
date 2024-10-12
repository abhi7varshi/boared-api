package com.example.boaredapi.data.remote

import com.example.boaredapi.data.models.Comment
import com.example.boaredapi.data.models.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceHolderApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{postId}/comments")
    suspend fun getCommentsForPost(@Path("postId") postId: Int): List<Comment>
}