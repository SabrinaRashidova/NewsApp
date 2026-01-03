package com.sabrina.newsapp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sabrina.newsapp.navigation.BottomBarScreen

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Bookmarks
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach {screen ->
            NavigationBarItem(
                label = { Text(text = screen.title) },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.iconRes),
                        contentDescription = screen.title,
                        modifier = Modifier.size(22.dp)
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}