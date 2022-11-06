package com.example.mobiledevelopment.src.profile

import com.example.mobiledevelopment.src.domain.retrofit.Common
import com.example.mobiledevelopment.src.domain.retrofit.ProfileModel
import com.example.mobiledevelopment.src.domain.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileRepository {
    private val service = Common.retrofitService

    fun getProfile(
        onResponseAction: (ProfileModel) -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int) -> Unit
    ) {
        service.getProfile("Bearer ${Common.userToken}").enqueue(object :
            Callback<ProfileModel> {
            override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                onFailureAction()
            }

            override fun onResponse(call: Call<ProfileModel>, response: Response<ProfileModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    onBadResponseAction(response.code())
                    return
                }

                Common.userId = response.body()?.id ?: ""
                response.body()?.let { onResponseAction(it) }
            }
        })
    }

    fun updateProfile(
        onResponseAction: () -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int) -> Unit,
        profileModel: ProfileModel
    ) {
        service.updateProfile("Bearer ${Common.userToken}", profileModel).enqueue(object :
            Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                onFailureAction()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    onBadResponseAction(response.code())
                    return
                }

                onResponseAction()
            }
        })
    }

    fun logout(action: () -> Unit) {
        service.logout("Bearer ${Common.userToken}").enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                action()

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                action()
            }
        })
        TokenManager.getInstance().dropToken()
    }
}