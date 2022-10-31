package com.example.mobiledevelopment.ui.theme.composes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.mobiledevelopment.R
import com.example.mobiledevelopment.ui.theme.*


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

