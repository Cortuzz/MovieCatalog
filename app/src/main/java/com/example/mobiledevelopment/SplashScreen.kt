package com.example.mobiledevelopment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.mobiledevelopment.src.domain.utils.SharedStorage
import com.example.mobiledevelopment.src.domain.utils.services.TokenProviderService
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashScreen: ComponentActivity() {
    private val authManager = TokenProviderService.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
        StrictMode.setThreadPolicy(policy)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            delay(3000)
            tokenChecker()

            val intent = Intent(this@SplashScreen, Application::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun tokenChecker() {
        authManager.loadToken()
        SharedStorage.isTokenValid = authManager.checkTokenSynchronously()
    }
}