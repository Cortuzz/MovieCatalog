package com.example.mobiledevelopment.src.domain.composes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
                color = TextColor
            )
        },

        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).onFocusChanged {
                focus -> focusState = focus.isFocused }.height(54.dp),
        colors = getInputTextColors(),
        value = text,
        onValueChange = { text = it; onChange(it) },
        visualTransformation = if (isHidden) PasswordVisualTransformation() else VisualTransformation.None,

        shape = RoundedCornerShape(8.dp)
    )
}


@Composable
fun InputText(label: String, value: MutableState<String>, onChange: () -> Unit, isHidden: Boolean = false, isDate: Boolean = false) {
    var focusState by rememberSaveable { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            placeholder = {
                Text(
                    text = if (focusState) "" else label,
                    fontFamily = IBMPlex,
                    fontWeight = FontWeight(400),
                    fontSize = 14.sp,
                    lineHeight = 12.sp,
                    color = TextColor
                )
            },

            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).onFocusChanged {
                    focus -> focusState = focus.isFocused }.height(54.dp),
            colors = getInputTextColors(),
            value = value.value,
            onValueChange = { value.value = it; onChange() },
            visualTransformation = if (isHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default.copy
                (keyboardType = if (isDate) KeyboardType.Number else KeyboardType.Text
            ),

            shape = RoundedCornerShape(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxSize().offset(y = 16.dp, x = (-34.25).dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isDate) DatePicker(date = value)
        }
    }
}

@Composable
fun ProfileInputText(value: MutableState<String>, isDate: Boolean = false, isNumber: Boolean = false) {
    Box {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = getInputTextColors(),
            value = value.value,
            onValueChange = { value.value = it; },
            keyboardOptions = KeyboardOptions.Default.copy
                (keyboardType = if (isDate || isNumber) KeyboardType.Number else KeyboardType.Text
            ),

            shape = RoundedCornerShape(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxSize().offset(y = 16.dp, x = (-18).dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isDate) DatePicker(date = value)
        }
    }
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