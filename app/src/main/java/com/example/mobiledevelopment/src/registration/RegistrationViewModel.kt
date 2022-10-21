package com.example.mobiledevelopment.src.registration

import androidx.compose.runtime.mutableStateOf
import com.example.mobiledevelopment.src.MainActivity
import com.example.mobiledevelopment.src.registration.domain.ViewField

class RegistrationViewModel(private val view: RegistrationView, private val activity: MainActivity) {
    private val repository = RegistrationRepository()
    private val fieldsModel = RegistrationFieldsModel()
    var fullness = mutableStateOf(false)

    fun handleRegistrationClick() {
        repository.registerUser(
            registerModel = fieldsModel.getUserRegisterModel(),
            onFailureAction = {
            },
            onResponseAction = {
                activity.setMainView()
            }
        )
    }

    fun handleLoginViewClick() {
        activity.setLoginView()
    }

    fun changeField(field: ViewField, value: String) {
        fieldsModel.changeField(field, value)
        fullness.value =  "" !in fieldsModel.getFields().values
    }

    fun getField(field: ViewField): String {
        return fieldsModel.getField(field)
    }
}
