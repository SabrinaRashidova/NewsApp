package com.sabrina.newsapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sabrina.newsapp.navigation.SetupNavGraph
import com.sabrina.newsapp.ui.common.BottomBar

@Composable
fun MainScreen(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {innerPadding->
        Box(modifier = Modifier.padding(innerPadding)){
            SetupNavGraph(navController = navController)
        }
    }
}