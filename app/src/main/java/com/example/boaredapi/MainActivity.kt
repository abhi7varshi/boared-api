package com.example.boaredapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.boaredapi.ui.theme.BoaredApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BoaredApiTheme {
                BoredApiApp()
            }
        }
    }
}

@Composable
fun BoredApiApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val commentsRoute = navController.currentBackStackEntryAsState().value?.toRoute<CommentsRoute>()

    val showBottomBar = commentsRoute?.postId == -1

    Scaffold(bottomBar = {
        AnimatedVisibility(
            visible = showBottomBar,
            enter = fadeIn(animationSpec = tween(300)) + slideInVertically(
                initialOffsetY = { it }, // Start from the bottom of the screen
                animationSpec = tween(300)
            ),
            exit = fadeOut(animationSpec = tween(300)) + slideOutVertically(
                targetOffsetY = { it }, // Slide out to the bottom of the screen
                animationSpec = tween(300)
            )
        ) {
            MyBottomNavigation(navController = navController)
        }
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = Home) {
                composable<Home> {
                    HomeScreen(navController = navController)
                }

                composable<Saved> {
                    SavedScreen()
                }

                composable<CommentsRoute> {
                    val args = it.toRoute<CommentsRoute>()

                    CommentsScreen(postId = args.postId, navController = navController)
                }
            }
        }
    }
}

@Composable
fun MyBottomNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }

    NavigationBar {
        TOP_LEVEL_ROUTES.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = index == selectedIndex.intValue,
                onClick = {
                    selectedIndex.intValue = index

                    //navigation
                    navController.navigate(TOP_LEVEL_ROUTES[index].route) {
                        popUpTo(Home)
                        launchSingleTop = true
                    }
                },
                label = { Text(destination.title) },
                icon = { Icon(imageVector = destination.icon, contentDescription = null) }
            )
        }
    }

}