package com.example.mobiledevelopment.src.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import com.example.mobiledevelopment.src.domain.login.AuthState
import com.example.mobiledevelopment.src.domain.login.LoginFieldsProvider
import com.example.mobiledevelopment.src.domain.login.ViewField

class LoginViewModel {
    private val repository = LoginRepository()
    private var fieldsModel = LoginFieldsProvider()
    var authState = mutableStateOf(AuthState.Idle)
    var fullness = mutableStateOf(false)
    var logoSize = mutableStateOf(0.dp)

    fun handleLoginClick(onResponse: () -> Unit) {
        authState.value = AuthState.Loading
        repository.tryLoginUser(
            loginModel = fieldsModel.getModel(),
            onFailureAction = {
                authState.value = AuthState.Error
            },
            onBadResponseAction = {
                authState.value = AuthState.WrongData
            },
            onResponseAction = {
                authState.value = AuthState.Idle
                fullness.value = false
                fieldsModel = LoginFieldsProvider()
                onResponse()
            }
        )
    }

    fun getField(field: ViewField): String {
        return fieldsModel.getField(field)
    }

    fun changeField(field: ViewField, value: String) {
        fieldsModel.changeField(field, value)
        fullness.value =  "" !in fieldsModel.getFields().values
    }

    fun handleRegistrationScreenClick() {
        logoSize.value = 100.dp
    }

    fun restoreSize() {
        logoSize.value = 170.dp
    }
}