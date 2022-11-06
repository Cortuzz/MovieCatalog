package com.example.mobiledevelopment.src.domain.login

import com.example.mobiledevelopment.include.retrofit.UserLoginModel
import com.example.mobiledevelopment.src.domain.FieldsProvider

class LoginFieldsProvider: FieldsProvider<ViewField, UserLoginModel> {
    private val viewFields = mutableMapOf(
        ViewField.Login to "",
        ViewField.Password to "",
    )

    override fun changeField(field: ViewField, value: String) {
        viewFields[field] = value
    }

    override fun getField(field: ViewField): String {
        return viewFields[field]!!
    }

    override fun getFields(): Map<ViewField, String> {
        return viewFields
    }

    override fun getModel(): UserLoginModel {
        return UserLoginModel(
            username = viewFields[ViewField.Login]!!,
            password = viewFields[ViewField.Password]!!
        )
    }
}