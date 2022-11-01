package com.example.mobiledevelopment.ui.theme.composes

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiledevelopment.ui.theme.AccentColor
import com.example.mobiledevelopment.ui.theme.DialogColor
import com.example.mobiledevelopment.ui.theme.IBMPlex
import com.example.mobiledevelopment.ui.theme.TextColor


@Composable
fun Dialog(
    title: String,
    text: String,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier.defaultMinSize(minHeight = 125.dp),
        backgroundColor = DialogColor,
        shape = RoundedCornerShape(8.dp),
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
                color = AccentColor,
                fontFamily = IBMPlex,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            )
        },
        text = {
            Text(
                text = text,
                color = TextColor,
                fontFamily = IBMPlex,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.5.sp,
            )
        },
        confirmButton = { }
    )
}

