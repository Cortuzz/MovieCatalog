package com.example.mobiledevelopment.src.domain

interface FieldsProvider<ViewField, Model> {
    fun changeField(field: ViewField, value: String)

    fun getField(field: ViewField): String

    fun getFields(): Map<ViewField, String>

    fun getModel(): Model
}