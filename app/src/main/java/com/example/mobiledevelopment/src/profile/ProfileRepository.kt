package com.example.mobiledevelopment.src.profile

import com.example.mobiledevelopment.src.domain.models.ProfileModel
import com.example.mobiledevelopment.src.domain.utils.SharedStorage
import com.example.mobiledevelopment.src.domain.utils.services.RequestsProviderService
import com.example.mobiledevelopment.src.domain.utils.services.TokenProviderService

class ProfileRepository {
    private val service = RequestsProviderService()

    fun getProfile(
        onResponseAction: (ProfileModel) -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int) -> Unit
    ) {
        service.getProfile(
            onBadResponseAction = { code, _ -> onBadResponseAction(code) },
            onFailureAction = { onFailureAction() },
            onResponseAction = { _, body ->
                onResponseAction(body)
                SharedStorage.userId = body.id
            }
        )
    }

    fun updateProfile(
        onResponseAction: () -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int) -> Unit,
        profileModel: ProfileModel
    ) {
        service.updateProfile(
            onResponseAction = { onResponseAction() },
            onFailureAction = { onFailureAction() },
            onBadResponseAction = { code, _ -> onBadResponseAction(code) },
            profileModel = profileModel
        )
    }

    fun logout(action: () -> Unit) {
        service.logout(
            onBadResponseAction = { _, _ -> action() },
            onFailureAction = { action() },
            onResponseAction = { action() }
        )
        TokenProviderService.getInstance().dropToken()
    }
}