package com.example.mobiledevelopment.src.domain.admin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ViewFieldProvider(
    val label: String,
    val isInt: Boolean = false,
    val value: MutableState<String> = mutableStateOf("")
)