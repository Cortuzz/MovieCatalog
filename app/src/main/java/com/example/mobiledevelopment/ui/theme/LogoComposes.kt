package com.example.mobiledevelopment.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.mobiledevelopment.R


@Composable
fun LogoScreen(logoAlignment: Alignment) {
    Surface(
        color = BackgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Logo(alignment = logoAlignment)
    }
}


@Composable
fun Logo(alignment: Alignment) {
    Box(contentAlignment = alignment) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo"
        )
    }
}