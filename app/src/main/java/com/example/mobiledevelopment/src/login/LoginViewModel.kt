package com.example.mobiledevelopment.src.login

import androidx.compose.runtime.mutableStateOf
import com.example.mobiledevelopment.src.MainActivity
import com.example.mobiledevelopment.src.login.domain.ViewField
import com.example.mobiledevelopment.src.registration.RegistrationRepository

class LoginViewModel(private val view: LoginView, private val activity: MainActivity) {
    private val repository = LoginRepository()
    var fullness = mutableStateOf(false)

    private val viewFields = mutableMapOf(
        ViewField.Login to "",
        ViewField.Password to "",
    )

    fun handleLoginClick() {
        repository.tryLoginUser(
            fields = viewFields,
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
        return viewFields[field]!!
    }

    fun changeField(field: ViewField, value: String) {
        viewFields[field] = value
        fullness.value =  "" !in viewFields.values
    }
}