package com.example.greychallenge.mvvm.routes

sealed class HomeRoute(val route: String) {
    data object Home : HomeRoute("home")
    data object Path : HomeRoute("home/path")
}
