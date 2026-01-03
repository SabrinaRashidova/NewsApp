package com.sabrina.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sabrina.newsapp.ui.bookmark.BookmarkScreen
import com.sabrina.newsapp.ui.home.HomeScreen

@Composable
fun SetupNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ){
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Bookmarks.route) {
            BookmarkScreen()
        }
    }
}