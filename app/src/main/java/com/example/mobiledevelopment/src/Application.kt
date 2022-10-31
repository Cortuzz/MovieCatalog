package com.example.mobiledevelopment.src

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobiledevelopment.src.Screen
import com.example.mobiledevelopment.src.login.LoginView
import com.example.mobiledevelopment.src.main.MainView
import com.example.mobiledevelopment.src.movie.MovieView
import com.example.mobiledevelopment.src.registration.RegistrationView
import com.example.mobiledevelopment.ui.theme.composes.RatingShape


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