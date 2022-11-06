package com.example.mobiledevelopment


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mobiledevelopment.src.domain.utils.Screen
import com.example.mobiledevelopment.src.domain.utils.SharedStorage
import com.example.mobiledevelopment.ui.theme.AccentColor
import com.example.mobiledevelopment.ui.theme.BackgroundColor


class Application : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (SharedStorage.isTokenValid) {
            launchApp(Screen.Main.route)
            return
        }
        launchApp(Screen.Login.route)
    }

    private fun launchApp(route: String) {
        setContent {
            MaterialTheme(colors = darkColors(primary = AccentColor, secondary = AccentColor)) {
                navController = rememberNavController()

                Surface(color = BackgroundColor,
                    modifier = Modifier.fillMaxSize()) {
                    SetupNavGraph(navController = navController, route)
                }
            }
        }
    }
}
