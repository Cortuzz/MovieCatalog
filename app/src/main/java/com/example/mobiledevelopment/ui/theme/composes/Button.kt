package com.example.mobiledevelopment.ui.theme.composes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiledevelopment.ui.theme.AccentColor
import com.example.mobiledevelopment.ui.theme.IBMPlex
import com.example.mobiledevelopment.ui.theme.OutlineColor


@Composable
fun PrimaryButton(name: String, action: () -> Unit, isEnabled: MutableState<Boolean> = mutableStateOf(true)) {
    OutlinedButton(onClick = action,
        colors = getButtonColors(),
        enabled = isEnabled.value,
        border = BorderStroke(1.dp, if (isEnabled.value) AccentColor else OutlineColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        ButtonText(text = name)
    }
}


@Composable
fun SecondaryButton(name: String, action: () -> Unit) {
    TextButton(onClick = action,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = AccentColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        ButtonText(text = name)
    }
}


@Composable
fun ButtonText(text: String) {
    Text(text = text,
        fontFamily = IBMPlex,
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp,
        modifier = Modifier.padding(top = 3.dp, bottom = 3.dp)
    )
}


@Composable
fun getButtonColors(): ButtonColors {
    return ButtonDefaults.buttonColors(
        disabledContentColor = AccentColor,
        disabledBackgroundColor = Color.Transparent,
        contentColor = Color.White,
        backgroundColor = AccentColor
    )
}
