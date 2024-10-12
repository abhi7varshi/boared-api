@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.boaredapi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.getPosts()
    }

    val posts by viewModel.posts.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Posts")
        })
    }) { innerPadding ->

        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(posts) { post ->
                ListItem(
                    leadingContent = {
                        Text("${post.id}")
                    },
                    headlineContent = { Text(post.title) },
                    trailingContent = {
                        IconButton(onClick = {
                            viewModel.savePost(post, isLiked = false)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.clickable {
                        navController.navigate(route = CommentsRoute(postId = post.id))
                    }
                )
            }
        }
    }
}