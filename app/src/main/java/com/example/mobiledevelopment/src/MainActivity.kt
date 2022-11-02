package com.example.mobiledevelopment.src


import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mobiledevelopment.R
import com.example.mobiledevelopment.ui.theme.AccentColor
import com.example.mobiledevelopment.ui.theme.BackgroundColor
import com.example.mobiledevelopment.ui.theme.MobileDevelopmentTheme

class MainActivity : ComponentActivity() {
    private val authManager = TokenManager.getInstance(this)
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
