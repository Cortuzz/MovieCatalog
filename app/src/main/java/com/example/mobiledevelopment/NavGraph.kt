package com.example.mobiledevelopment

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobiledevelopment.src.admin.AdminScreen
import com.example.mobiledevelopment.src.domain.utils.Screen
import com.example.mobiledevelopment.src.login.LoginScreen
import com.example.mobiledevelopment.src.main.FullMainScreen
import com.example.mobiledevelopment.src.main.MainScreen
import com.example.mobiledevelopment.src.movie.MovieScreen
import com.example.mobiledevelopment.src.profile.ProfileScreen
import com.example.mobiledevelopment.src.registration.RegistrationScreen


@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    val navigateToLogin = { navController.navigate(Screen.Login.route) { popUpTo(0) } }
    val navigateToRegistration = { navController.navigate(Screen.Registration.route) { popUpTo(0) } }
    val navigateToMain = { navController.navigate(Screen.FullMain.route) { popUpTo(0) } }
    val navigateToMovie = { navController.navigate(Screen.Movie.route) }
    val navigateToAdmin = { navController.navigate(Screen.Admin.route) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                navToMain = navigateToMain,
                navToRegistration = navigateToRegistration
            )
        }
        composable(route = Screen.Registration.route) {
            RegistrationScreen(
                navToLogin = navigateToLogin,
                navToMain = navigateToMain
            )
        }
        composable(route = Screen.FullMain.route) {
            FullMainScreen(
                navToLogin = navigateToLogin,
                navToMovie = navigateToMovie,
                navToAdmin = navigateToAdmin
            )
        }
        composable(route = Screen.Movie.route) {
            MovieScreen(
                navToLogin = navigateToLogin,
                navToMain = navigateToMain
            )
        }
        composable(route = Screen.Admin.route) {
            AdminScreen()
        }
    }
}