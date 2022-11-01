package com.example.mobiledevelopment.src


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mobiledevelopment.ui.theme.BackgroundColor
import com.example.mobiledevelopment.ui.theme.MobileDevelopmentTheme

class MainActivity : ComponentActivity() {
    private val authManager = TokenManager.getInstance(this)
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authManager.loadToken()

        setContent {
            MobileDevelopmentTheme {
                navController = rememberNavController()

                Surface(color = BackgroundColor,
                    modifier = Modifier.fillMaxSize()) {
                    SetupNavGraph(navController = navController)
                }
            }
        }

        authManager.checkToken { navController.navigate("main_screen") }
    }
}
