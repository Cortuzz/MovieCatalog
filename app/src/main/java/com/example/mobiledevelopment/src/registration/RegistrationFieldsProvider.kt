package com.example.mobiledevelopment.src.registration

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mobiledevelopment.include.retrofit.UserRegisterModel
import com.example.mobiledevelopment.src.domain.FieldsProvider
import com.example.mobiledevelopment.src.registration.domain.ViewField
import com.example.mobiledevelopment.src.utils.Utils

class RegistrationFieldsProvider : FieldsProvider<ViewField, UserRegisterModel> {
    private val viewFields = mutableMapOf(
        ViewField.Login to mutableStateOf(""),
        ViewField.Email to mutableStateOf(""),
        ViewField.Name to mutableStateOf(""),
        ViewField.Password to mutableStateOf(""),
        ViewField.RepeatPassword to mutableStateOf(""),
        ViewField.DateOfBirth to mutableStateOf(""),
        ViewField.Gender to mutableStateOf("")
    )

    private val correctValues = mutableMapOf(
        ViewField.Login to mutableStateOf(true),
        ViewField.Email to mutableStateOf(true),
        ViewField.Name to mutableStateOf(true),
        ViewField.Password to mutableStateOf(true),
        ViewField.RepeatPassword to mutableStateOf(true),
        ViewField.DateOfBirth to mutableStateOf(true)
    )

    fun checkCorrect(): Boolean {
        val pass = getField(ViewField.Password)
        val rPass = getField(ViewField.RepeatPassword)

        getCorrectValue(ViewField.Login).value = getField(ViewField.Login).length >= 6
        getCorrectValue(ViewField.Name).value = getField(ViewField.Name).length >= 3
        getCorrectValue(ViewField.Password).value = pass.length >= 6
        getCorrectValue(ViewField.RepeatPassword).value = pass == rPass
        getCorrectValue(ViewField.Email).value = checkEmail(getField(ViewField.Email))
        getCorrectValue(ViewField.DateOfBirth).value = checkDate(getField(ViewField.DateOfBirth))

        for (value in correctValues.values) {
            if (!value.value)
                return false
        }
        return true
    }

    override fun changeField(field: ViewField, value: String) {
        viewFields[field]?.value = value
    }

    override fun getField(field: ViewField): String {
        return viewFields[field]!!.value
    }

    fun getMutableState(field: ViewField): MutableState<String> {
        return viewFields[field]!!
    }

    override fun getFields(): Map<ViewField, String> {
        val tMap = mutableMapOf<ViewField, String>()

        for (field in viewFields) {
            tMap[field.key] = field.value.value
        }
        return tMap
    }
    
    override fun getModel(): UserRegisterModel {
        return UserRegisterModel(
            userName = viewFields[ViewField.Login]!!.value,
            name = viewFields[ViewField.Name]!!.value,
            password = viewFields[ViewField.Password]!!.value,
            email = viewFields[ViewField.Email]!!.value,
            birthDate = formatDate(viewFields[ViewField.DateOfBirth]!!.value),
            gender = viewFields[ViewField.Gender]?.value?.toInt()
        )
    }

    fun getCorrectValue(field: ViewField): MutableState<Boolean> {
        return correctValues[field]!!
    }

    private fun formatDate(date: String?): String? {
        val sDate = date?.split('.')?.reversed()
        return sDate?.joinToString("-")
    }

    private fun checkEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkDate(date: String): Boolean {
        return Utils.isValidBirthDate(date)
    }
}