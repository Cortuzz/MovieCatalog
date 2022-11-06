package com.example.mobiledevelopment.src.login

import android.util.Log
import com.example.mobiledevelopment.src.domain.retrofit.Common
import com.example.mobiledevelopment.src.domain.utils.services.TokenProviderService
import com.example.mobiledevelopment.src.domain.models.UserLoginModel
import com.example.mobiledevelopment.src.domain.models.UserTokenModel
import com.example.mobiledevelopment.src.domain.utils.SharedStorage
import com.example.mobiledevelopment.src.domain.utils.services.RequestsProviderService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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