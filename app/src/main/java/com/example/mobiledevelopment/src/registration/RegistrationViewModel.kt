package com.example.mobiledevelopment.src.registration

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import com.example.mobiledevelopment.src.domain.registration.RegistrationFieldsProvider
import com.example.mobiledevelopment.src.domain.registration.RegistrationState
import com.example.mobiledevelopment.src.domain.registration.ViewField


class RegistrationViewModel {
    private val repository = RegistrationRepository()
    private var fieldsModel = RegistrationFieldsProvider()
    var registrationState = mutableStateOf(RegistrationState.Idle)

    var fullness = mutableStateOf(false)
    var logoSize = mutableStateOf(0.dp)

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
                registrationState.value = RegistrationState.Idle
                fullness.value = false
                fieldsModel = RegistrationFieldsProvider()
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

    fun handleLoginScreenClick() {
        logoSize.value = 170.dp
    }

    fun restoreSize() {
        logoSize.value = 100.dp
    }
}
