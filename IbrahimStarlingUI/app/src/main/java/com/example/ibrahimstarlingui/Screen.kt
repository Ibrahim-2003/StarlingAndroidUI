package com.example.ibrahimstarlingui

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object HealthScreen : Screen("health_screen")
    object ProfileScreen : Screen("profile_screen")
}