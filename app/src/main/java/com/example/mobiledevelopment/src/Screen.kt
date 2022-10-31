package com.example.mobiledevelopment.src

import androidx.compose.runtime.Composable
import com.example.mobiledevelopment.src.login.LoginView

sealed class Screen(val route: String) {
    object Login: Screen(route = "login_screen")
    object Registration: Screen(route = "registration_screen")
    object Main: Screen(route = "main_screen")
    object Movie: Screen(route = "movie_screen")
}
