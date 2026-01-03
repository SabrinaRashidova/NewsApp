package com.sabrina.newsapp.navigation

sealed class Screen(val route: String){
    object Home: Screen("home")
    object Bookmarks: Screen("bookmarks")
}