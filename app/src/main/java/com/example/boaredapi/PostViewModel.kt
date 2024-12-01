package com.example.boaredapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boaredapi.data.local.PostEntity
import com.example.boaredapi.data.local.toEntity
import com.example.boaredapi.data.models.Comment
import com.example.boaredapi.data.models.Post
import com.example.boaredapi.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    val savedPosts = repository
        .getAllSavedPost()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            _posts.value = repository.getPosts()
            _isLoading.value = false
        }
    }

    fun savePost(post: Post, isLiked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.savePost(post.toEntity(isLiked))
        }
    }

    fun removeSavedPost(post: PostEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeSavedPost(post)
        }
    }

    fun updateIsLikedStatus(post: PostEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateIsLikedStatus(post)
        }
    }

    fun getCommentsForPost(postId: Int) {
        viewModelScope.launch {
            _comments.value = repository.getCommentsForPost(postId)
        }
    }
}