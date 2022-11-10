package com.example.mobiledevelopment.src.domain.utils

sealed class Screen(val route: String) {
    object Login: Screen(route = "login_screen")
    object Registration: Screen(route = "registration_screen")
    object Main: Screen(route = "main_screen")
    object FullMain: Screen(route = "full_main_screen")
    object Movie: Screen(route = "movie_screen")
    object Profile: Screen(route = "profile_screen")
    object Admin: Screen(route = "admin_screen")
}
