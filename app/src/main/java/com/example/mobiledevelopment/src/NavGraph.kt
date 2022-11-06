package com.example.mobiledevelopment.src

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobiledevelopment.src.domain.utils.Screen
import com.example.mobiledevelopment.src.login.LoginScreen
import com.example.mobiledevelopment.src.main.MainScreen
import com.example.mobiledevelopment.src.movie.MovieScreen
import com.example.mobiledevelopment.src.profile.ProfileScreen
import com.example.mobiledevelopment.src.registration.RegistrationScreen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator


@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    val navigateToLogin = { navController.navigate("login_screen") { popUpTo(0) } }
    val navigateToRegistration = { navController.navigate("registration_screen") { popUpTo(0) } }
    val navigateToMain = { navController.navigate("main_screen") { popUpTo(0) } }
    val navigateToProfile = { navController.navigate("profile_screen") { popUpTo(0) } }
    val navigateToMovie = { navController.navigate("movie_screen") }

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
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
        composable(route = Screen.Main.route) {

            MainScreen(
                navToLogin = navigateToLogin,
                navToMovie = navigateToMovie,
                navToProfile = navigateToProfile
            )
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                navToLogin = navigateToLogin,
                navToMain = navigateToMain
            )
        }
        composable(route = Screen.Movie.route) {
            //viewModel.getMovie()
            //viewModel.clearReview()
            MovieScreen(
                navToLogin = navigateToLogin,
                navToMain = navigateToMain
            )
        }
    }
}