package com.example.mobiledevelopment.src.domain.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiledevelopment.src.domain.utils.ColorGenerator
import com.example.mobiledevelopment.ui.theme.IBMPlex

@Composable
fun RatingShape(rating: Float, modifier: Modifier) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(ColorGenerator.getColor(rating))
        ) {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = if (rating.isNaN()) "—" else rating.toString(),
                color = Color.White,
                fontFamily = IBMPlex,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.5.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun RatingShape(rating: Int, modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(ColorGenerator.getColor(rating.toFloat()))
    ) {
        Text(
            modifier = Modifier.fillMaxSize(),
            text = rating.toString(),
            color = Color.White,
            fontFamily = IBMPlex,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.5.sp,
            textAlign = TextAlign.Center
        )
    }
}