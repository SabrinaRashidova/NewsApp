package com.sabrina.newsapp.navigation

import androidx.compose.ui.graphics.painter.Painter
import com.sabrina.newsapp.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val iconRes: Int
) {
    object Home : BottomBarScreen("home","Home", R.drawable.ic_home)
    object Bookmarks : BottomBarScreen("bookmarks","Saved", R.drawable.bookmark_filled)
}