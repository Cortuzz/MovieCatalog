package com.example.mobiledevelopment.src.domain.composes

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiledevelopment.ui.theme.AccentColor
import com.example.mobiledevelopment.ui.theme.IBMPlex

@Composable
fun WrongFieldText(text: String, correct: MutableState<Boolean>) {
    if (correct.value)
        return

    Text(
        modifier = Modifier.padding(horizontal = 18.dp),
        text = text,
        color = AccentColor,
        fontFamily = IBMPlex,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp,
    )
}