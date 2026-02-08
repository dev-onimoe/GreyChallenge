package com.example.greychallenge

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.greychallenge.mvvm.routes.HomeRoute
import com.example.greychallenge.mvvm.routes.homeNavGraph

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute.Home.route
    ) {
        homeNavGraph(navController)
    }
}
