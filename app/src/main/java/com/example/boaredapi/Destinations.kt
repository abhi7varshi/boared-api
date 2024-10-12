package com.example.boaredapi

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

//tabs
data class TopLevelRoute<T : Any>(
    val route: T,
    val title: String,
    val icon: ImageVector
)

@Serializable
data object Home

@Serializable
data object Saved

val TOP_LEVEL_ROUTES = listOf(
    TopLevelRoute(route = Home, title = "Home", icon = Icons.Default.Home),
    TopLevelRoute(route = Saved, title = "Saved", icon = Icons.Default.Menu)
)


//screens
@Serializable
data class CommentsRoute(
    val postId: Int = -1
)

