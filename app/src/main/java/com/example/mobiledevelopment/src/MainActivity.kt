package com.example.mobiledevelopment.src


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.example.mobiledevelopment.src.domain.Drawable
import com.example.mobiledevelopment.src.login.LoginView
import com.example.mobiledevelopment.src.main.MainView
import com.example.mobiledevelopment.src.registration.RegistrationView
import com.example.mobiledevelopment.ui.theme.BackgroundColor
import com.example.mobiledevelopment.ui.theme.MobileDevelopmentTheme

class MainActivity : ComponentActivity() {
    private lateinit var currentView: Drawable
    private val authManager = TokenManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLoginView()

        authManager.loadToken()
        authManager.checkToken()
    }

    fun setLoginView() {
        currentView = LoginView.getInstance(this)
        draw()
    }

    fun setRegistrationView() {
        currentView = RegistrationView.getInstance(this)
        draw()
    }

    fun setMainView() {
        currentView = MainView.getInstance(this)
        draw()
    }

    private fun draw() {
        setContent {
            MobileDevelopmentTheme {
                Surface(color = BackgroundColor,
                    modifier = Modifier.fillMaxSize()) {
                    currentView.Draw()
                }
            }
        }
    }
}
