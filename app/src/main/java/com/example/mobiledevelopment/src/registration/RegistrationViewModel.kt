package com.example.mobiledevelopment.src.registration

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mobiledevelopment.src.main.domain.RegistrationState
import com.example.mobiledevelopment.src.registration.domain.ViewField


class RegistrationViewModel {
    private val repository = RegistrationRepository()
    private val fieldsModel = RegistrationFieldsProvider()
    var registrationState = mutableStateOf(RegistrationState.Idle)

    var fullness = mutableStateOf(false)

    fun handleRegistrationClick(onResponse: () -> Unit) {
        if (!fieldsModel.checkCorrect())
            return

        registrationState.value = RegistrationState.Loading

        repository.registerUser(
            registerModel = fieldsModel.getModel(),
            onBadResponseAction = { code, body ->
                if (code == 400 && body.string().contains("DuplicateUserName")) {
                    registrationState.value = RegistrationState.UserExist
                    return@registerUser
                }
                registrationState.value = RegistrationState.InternalError
            },
            onFailureAction = {
                  registrationState.value = RegistrationState.Error
            },
            onResponseAction = {
                onResponse()
            }
        )
    }

    fun changeField(field: ViewField, value: String) {
        fieldsModel.changeField(field, value)
        checkFullness()
    }

    fun isFieldCorrect(field: ViewField): MutableState<Boolean> {
        return fieldsModel.getCorrectValue(field)
    }

    fun checkFullness() {
        fullness.value =  "" !in fieldsModel.getFields().values
    }

    fun getMutableState(field: ViewField): MutableState<String> {
        return fieldsModel.getMutableState(field)
    }
}
