package com.example.ibrahimstarlingui

sealed class Screen(val route: String) {
    data object MainScreen : Screen("main_screen")
    data object HealthScreen : Screen("health_screen")
    data object ProfileScreen : Screen("profile_screen")
}