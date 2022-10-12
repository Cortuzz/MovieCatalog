package com.example.mobiledevelopment

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.ui.Alignment
import com.example.mobiledevelopment.ui.theme.LogoScreen
import com.example.mobiledevelopment.ui.theme.MobileDevelopmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        setContent {
            MobileDevelopmentTheme {
                LogoScreen(logoAlignment = Alignment.Center)
                Button(onClick = {nextActivity()}) {}
            }
        }
    }

    fun nextActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
