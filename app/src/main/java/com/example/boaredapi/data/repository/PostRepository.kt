package com.example.boaredapi.data.repository

import com.example.boaredapi.data.local.PostDao
import com.example.boaredapi.data.local.PostEntity
import com.example.boaredapi.data.models.Comment
import com.example.boaredapi.data.models.Post
import com.example.boaredapi.data.remote.JsonPlaceHolderApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(
    private var networkApi: JsonPlaceHolderApi,
    private val postDao: PostDao
) {
    //posts
    suspend fun getPosts(): List<Post> = networkApi.getPosts()

    suspend fun savePost(post: PostEntity) = postDao.savePost(post)

    suspend fun removeSavedPost(post: PostEntity) = postDao.removePost(post)

    suspend fun updateIsLikedStatus(post: PostEntity) = postDao.updateIsLikedStatus(post)

    fun getAllSavedPost(): Flow<List<PostEntity>> = postDao.getAllPosts()

    //comments
    suspend fun getCommentsForPost(postId: Int): List<Comment> =
        networkApi.getCommentsForPost(postId)
}