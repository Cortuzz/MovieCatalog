package com.example.mobiledevelopment.src.domain.composes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.mobiledevelopment.R


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
