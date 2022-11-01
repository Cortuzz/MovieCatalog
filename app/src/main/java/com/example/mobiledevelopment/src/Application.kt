package com.example.mobiledevelopment.src

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobiledevelopment.src.login.LoginView
import com.example.mobiledevelopment.src.main.MainView
import com.example.mobiledevelopment.src.movie.MovieView
import com.example.mobiledevelopment.src.registration.RegistrationView


@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(
            route = Screen.Login.route
        ) {
            LoginView(navController).Draw()
        }
        composable(
            route = Screen.Registration.route
        ) {
            RegistrationView(navController).Draw()
        }
        composable(
            route = Screen.Main.route
        ) {
            MainView.getInstance(navController).Draw()
        }
        composable(
            route = Screen.Movie.route
        ) {
            MovieView.getInstance(navController).Draw()
        }
    }
}