package com.example.mobiledevelopment.src.registration

import com.example.mobiledevelopment.include.retrofit.UserRegisterModel
import com.example.mobiledevelopment.src.registration.domain.ViewField

class RegistrationFieldsModel {
    private val viewFields = mutableMapOf(
        ViewField.Login to "",
        ViewField.Email to "",
        ViewField.Name to "",
        ViewField.Password to "",
        ViewField.RepeatPassword to "",
        ViewField.DateOfBirth to "",
        ViewField.Gender to ""
    )

    fun changeField(field: ViewField, value: String) {
        viewFields[field] = value
    }

    fun getField(field: ViewField): String {
        return viewFields[field]!!
    }

    fun getFields(): Map<ViewField, String> {
        return viewFields
    }
    
    fun getUserRegisterModel(): UserRegisterModel {
        return UserRegisterModel(
            userName = viewFields[ViewField.Login]!!,
            name = viewFields[ViewField.Name]!!,
            password = viewFields[ViewField.Password]!!,
            email = viewFields[ViewField.Email]!!,
            birthDate = formatDate(viewFields[ViewField.DateOfBirth]),
            gender = viewFields[ViewField.Gender]?.toInt()
        )
    }

    private fun formatDate(date: String?): String? {
        val sDate = date?.split('.')?.reversed()
        return sDate?.joinToString("-")
    }
}