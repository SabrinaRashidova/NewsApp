package com.sabrina.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sabrina.newsapp.ui.bookmark.BookmarkScreen
import com.sabrina.newsapp.ui.home.HomeScreen

@Composable
fun SetupNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToBookmarks = {
                    navController.navigate(Screen.Bookmarks.route)
                }
            )
        }
        composable(route = Screen.Bookmarks.route) {
            BookmarkScreen()
        }
    }
}