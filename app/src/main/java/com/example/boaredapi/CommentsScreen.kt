@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.boaredapi

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun CommentsScreen(
    modifier: Modifier = Modifier,
    postId: Int,
    viewModel: PostViewModel = hiltViewModel(),
    navController: NavController,
) {
    val comments by viewModel.comments.collectAsStateWithLifecycle()

    //like init function
    LaunchedEffect(Unit) {
        viewModel.getCommentsForPost(postId)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Comments") }, navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            })
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(comments) { comment ->
                ListItem(
                    leadingContent = { Text("${comment.id}") },
                    headlineContent = { Text(comment.name) },
                    supportingContent = { Text("by: ${comment.email}") },
                    trailingContent = {
                        IconButton(onClick = {

                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    },
                )
            }
        }
    }
}