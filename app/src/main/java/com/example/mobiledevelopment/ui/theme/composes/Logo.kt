package com.example.mobiledevelopment.ui.theme.composes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.mobiledevelopment.R
import com.example.mobiledevelopment.ui.theme.BackgroundColor


@Composable
fun LogoScreen(modifier: Modifier, logoAlignment: Alignment) {
    Surface(
        color = BackgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Logo(modifier, logoAlignment)
    }
}


@Composable
fun Logo(modifier: Modifier, alignment: Alignment) {
    Box(contentAlignment = alignment) {
       LogoImage(modifier)
    }
}


@Composable
fun LogoImage(modifier: Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Logo"
    )
}
