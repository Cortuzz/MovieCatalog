package com.example.mobiledevelopment.src.registration

import android.util.Log
import com.example.mobiledevelopment.src.domain.retrofit.Common
import com.example.mobiledevelopment.src.domain.utils.services.TokenProviderService
import com.example.mobiledevelopment.src.domain.models.UserRegisterModel
import com.example.mobiledevelopment.src.domain.models.UserTokenModel
import com.example.mobiledevelopment.src.domain.utils.SharedStorage
import com.example.mobiledevelopment.src.domain.utils.services.RequestsProviderService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationRepository {
    private val service = RequestsProviderService()

    fun registerUser(
        registerModel: UserRegisterModel,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int, ResponseBody) -> Unit,
        onResponseAction: () -> Unit,
    ) {
        service.registerUser(
            onBadResponseAction = { code, body -> onBadResponseAction(code, body) },
            onResponseAction = { _, body ->
                onResponseAction()
                SharedStorage.userToken = body.token
                TokenProviderService.getInstance().saveToken()
            },
            onFailureAction = { onFailureAction() },
            registerModel = registerModel
        )
    }
}
