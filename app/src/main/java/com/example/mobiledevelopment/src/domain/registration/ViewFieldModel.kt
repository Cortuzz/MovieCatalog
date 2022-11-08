package com.example.mobiledevelopment.src.domain.registration

data class ViewFieldModel(
    val name: String,
    val viewField: ViewField,
    val isHidden: Boolean,
    val errorText: String
)

val viewFieldModels = listOf(
    ViewFieldModel(loginText, ViewField.Login, false, loginWrongText),
    ViewFieldModel(emailText, ViewField.Email, false, emailWrongText),
    ViewFieldModel(nameText, ViewField.Name, false, nameWrongText),
    ViewFieldModel(passwordText, ViewField.Password, true, passwordWrongText),
    ViewFieldModel(repeatPasswordText, ViewField.RepeatPassword, true, repeatPasswordWrongText),
    ViewFieldModel(birthDateText, ViewField.DateOfBirth, false, birthDateWrongText)
)