package com.example.mobiledevelopment.src.login

import androidx.compose.runtime.mutableStateOf
import com.example.mobiledevelopment.src.MainActivity
import com.example.mobiledevelopment.src.login.domain.ViewField
import com.example.mobiledevelopment.src.registration.RegistrationRepository

class LoginViewModel(private val view: LoginView, private val activity: MainActivity) {
    private val repository = LoginRepository()
    private val fieldsModel = LoginFieldsProvider()
    var fullness = mutableStateOf(false)

    fun handleLoginClick() {
        repository.tryLoginUser(
            loginModel = fieldsModel.getModel(),
            onFailureAction = {
                println("FAILED")
            },
            onResponseAction = {
                activity.setMainView()
            }
        )
    }

    fun handleRegistrationViewClick() {
        activity.setRegistrationView()
    }

    fun getField(field: ViewField): String {
        return fieldsModel.getField(field)
    }

    fun changeField(field: ViewField, value: String) {
        fieldsModel.changeField(field, value)
        fullness.value =  "" !in fieldsModel.getFields().values
    }
}