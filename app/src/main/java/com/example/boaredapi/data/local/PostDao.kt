package com.example.boaredapi.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    fun getAllPosts(): Flow<List<PostEntity>> // if using Flow no need of suspend

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePost(post: PostEntity)

    @Delete
    suspend fun removePost(post: PostEntity)

    @Update
    suspend fun updateIsLikedStatus(post: PostEntity)
}