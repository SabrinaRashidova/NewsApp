package com.sabrina.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sabrina.newsapp.ui.bookmark.BookmarkScreen
import com.sabrina.newsapp.ui.detail.DetailsScreen
import com.sabrina.newsapp.ui.home.HomeScreen

@Composable
fun SetupNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ){
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Bookmarks.route) {
            BookmarkScreen()
        }
        composable(
            route = "details/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) {backStackEntry->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            DetailsScreen(
                url = url,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}