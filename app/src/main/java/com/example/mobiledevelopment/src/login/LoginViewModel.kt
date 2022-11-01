package com.example.mobiledevelopment.src.login

import androidx.compose.runtime.mutableStateOf
import com.example.mobiledevelopment.src.login.domain.AuthState
import com.example.mobiledevelopment.src.login.domain.ViewField

class LoginViewModel {
    private val repository = LoginRepository()
    private val fieldsModel = LoginFieldsProvider()
    var authState = mutableStateOf(AuthState.Idle)
    var fullness = mutableStateOf(false)

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
}