package com.example.mobiledevelopment.src.domain.composes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiledevelopment.ui.theme.*

@Composable
fun NavigationButton(
    name: String,
    onClick: () -> Unit,
    painter: Painter,
    fraction: Float
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(fraction),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = NavigationColor,
            contentColor = NavigationColor
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                contentDescription = null,
            )

            Text(
                text = name,
                fontFamily = IBMPlex,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = OutlineColor,
            )
        }
    }
}