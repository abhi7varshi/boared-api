@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.boaredapi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SavedScreen(modifier: Modifier = Modifier, viewModel: PostViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Saved")
        })
    }) { innerPadding ->
        val savedPost by viewModel.savedPosts.collectAsStateWithLifecycle()

        if (savedPost.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                //If not using box with fillMaxSize() we need to use innerPadding of
                // the scaffold to be displayed in the screen
                //Text("There are no saved posts.", modifier = Modifier.padding(innerPadding)
                Text("There are no saved posts.", color = Color.White)
            }
        } else {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(savedPost) { post ->
                    ListItem(leadingContent = {
                        Text("${post.id}")
                    }, headlineContent = {
                        Text(post.title)
                    }, trailingContent = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            if (post.isLiked) {
                                IconButton(onClick = {
                                    //like and unlike here
                                    viewModel.updateIsLikedStatus(post.copy(isLiked = false))
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = null
                                    )
                                }
                            } else {
                                IconButton(onClick = {
                                    //like and unlike here
                                    viewModel.updateIsLikedStatus(post.copy(isLiked = true))
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        contentDescription = null
                                    )
                                }
                            }

                            IconButton(onClick = {
                                viewModel.removeSavedPost(post)
                            }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                            }
                        }
                    })
                }
            }
        }
    }
}