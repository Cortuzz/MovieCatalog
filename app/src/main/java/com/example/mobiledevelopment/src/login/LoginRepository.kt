package com.example.mobiledevelopment.src.login

import com.example.mobiledevelopment.src.domain.models.UserLoginModel
import com.example.mobiledevelopment.src.domain.utils.SharedStorage
import com.example.mobiledevelopment.src.domain.utils.services.RequestsProviderService
import com.example.mobiledevelopment.src.domain.utils.services.TokenProviderService

class LoginRepository {
    private val service = RequestsProviderService()

    fun tryLoginUser(
        loginModel: UserLoginModel,
        onResponseAction: () -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: () -> Unit,
    ) {
        service.loginUser(
            onResponseAction = { _, body ->
               onResponseAction()
                SharedStorage.userToken = body.token
                TokenProviderService.getInstance().saveToken()
            },
            onFailureAction = { onFailureAction() },
            onBadResponseAction = { _, _ -> onBadResponseAction() },
            loginModel = loginModel
        )
    }
}