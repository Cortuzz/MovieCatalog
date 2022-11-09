package com.example.mobiledevelopment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.mobiledevelopment.src.domain.composes.Dialog
import com.example.mobiledevelopment.src.domain.login.errorText
import com.example.mobiledevelopment.src.domain.login.globalErrorText
import com.example.mobiledevelopment.src.domain.utils.SharedStorage
import com.example.mobiledevelopment.src.domain.utils.services.TokenProviderService
import com.example.mobiledevelopment.ui.theme.BackgroundColor
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashScreen: ComponentActivity() {
    private val authManager = TokenProviderService.getInstance(this)
    private lateinit var splashScreen: androidx.core.splashscreen.SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            delay(3000)
            tokenChecker()
        }
    }

    private fun goToApplication() {
        val intent = Intent(this@SplashScreen, Application::class.java)
        startActivity(intent)
        finish()
    }

    private fun tokenChecker() {
        //authManager.loadToken()
        authManager.checkServerAndToken(
            onResponseAction = {
                SharedStorage.isTokenValid = true
                goToApplication()
            },
            onBadResponseAction = {
                SharedStorage.isTokenValid = false
                goToApplication()
            },
            onFailureAction = {
                splashScreen.setKeepOnScreenCondition { false }
                alert()
            }
        )
    }

    private fun alert() {
        setContent {
            MaterialTheme {
                Box(
                    modifier = Modifier.fillMaxSize().background(BackgroundColor)
                ) {
                    Dialog(
                        onDismissRequest = { finishAndRemoveTask() },
                        title = globalErrorText,
                        text = errorText
                    )
                }
            }
        }
    }
}