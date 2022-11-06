package com.example.mobiledevelopment.src.domain.composes

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mobiledevelopment.ui.theme.AccentColor

@Composable
fun LoadingIndicator(modifier: Modifier, passState: () -> Boolean) {
    if (!passState())
        return

    CircularProgressIndicator(
        color = AccentColor,
        modifier = modifier
    )
}