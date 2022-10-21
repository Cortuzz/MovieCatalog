package com.example.mobiledevelopment.ui.theme.composes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiledevelopment.ui.theme.AccentColor
import com.example.mobiledevelopment.ui.theme.IBMPlex
import com.example.mobiledevelopment.ui.theme.OutlineColor
import com.example.mobiledevelopment.ui.theme.TextColor


@Composable
fun InputText(label: String, value: String, onChange: (value: String) -> Unit, isHidden: Boolean = false) {
    var text by rememberSaveable { mutableStateOf(value) }
    var focusState by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        placeholder = {
            Text(
                text = if (focusState) "" else label,
                fontFamily = IBMPlex,
                fontWeight = FontWeight(400),
                fontSize = 14.sp,
                lineHeight = 12.sp,
                modifier = Modifier.offset(y = (-3).dp),
                color = TextColor
            )
        },

        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).onFocusChanged {
                focus -> focusState = focus.isFocused }.height(50.dp),
        colors = getInputTextColors(),
        value = text,
        onValueChange = { text = it; onChange(it) },
        visualTransformation = if (isHidden) PasswordVisualTransformation() else VisualTransformation.None,

        shape = RoundedCornerShape(8.dp)
    )
}


@Composable
fun getInputTextColors(): TextFieldColors  {
    return TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = OutlineColor,
        unfocusedBorderColor = OutlineColor,
        textColor = AccentColor,
        focusedLabelColor = OutlineColor,
        unfocusedLabelColor = OutlineColor,
        placeholderColor = OutlineColor
    )
}