package com.example.mobiledevelopment.src


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
import com.example.mobiledevelopment.src.domain.utils.services.TokenProviderService
import com.example.mobiledevelopment.ui.theme.AccentColor
import com.example.mobiledevelopment.ui.theme.BackgroundColor

class Application : ComponentActivity() {
    private val authManager = TokenProviderService.getInstance(this)
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authManager.loadToken()

        setContent {
            MaterialTheme(colors = darkColors(primary = AccentColor, secondary = AccentColor)) {
                navController = rememberNavController()
                authManager.checkToken { navController.navigate("main_screen") {
                    popUpTo(0)
                } }

                Surface(color = BackgroundColor,
                    modifier = Modifier.fillMaxSize()) {
                    SetupNavGraph(navController = navController)
                }
            }
        }
    }
}
