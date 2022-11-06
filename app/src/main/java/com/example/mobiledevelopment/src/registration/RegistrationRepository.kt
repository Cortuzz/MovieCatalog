package com.example.mobiledevelopment.src.registration

import com.example.mobiledevelopment.src.domain.models.UserRegisterModel
import com.example.mobiledevelopment.src.domain.utils.SharedStorage
import com.example.mobiledevelopment.src.domain.utils.services.RequestsProviderService
import com.example.mobiledevelopment.src.domain.utils.services.TokenProviderService
import okhttp3.ResponseBody

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
