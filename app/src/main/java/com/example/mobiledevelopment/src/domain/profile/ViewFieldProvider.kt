package com.example.mobiledevelopment.src.domain.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ViewFieldProvider(
    val value: MutableState<String> = mutableStateOf(""),
    val initialValue: MutableState<String> = mutableStateOf(""),
    val isCorrect: MutableState<Boolean> = mutableStateOf(true),

    val label: String = "",
    val wrongText: String = "",
    val isDate: Boolean = false
) {
    fun initValue(viewValue: String) {
        value.value = viewValue
        initialValue.value = viewValue
    }
}
