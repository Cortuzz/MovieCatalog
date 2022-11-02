package com.example.mobiledevelopment.src.registration

import com.example.mobiledevelopment.include.retrofit.Common
import com.example.mobiledevelopment.include.retrofit.UserRegisterModel
import com.example.mobiledevelopment.include.retrofit.UserTokenModel
import com.example.mobiledevelopment.src.TokenManager
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationRepository {
    private val service = Common.retrofitService

    fun registerUser(
        registerModel: UserRegisterModel,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int, ResponseBody) -> Unit,
        onResponseAction: () -> Unit,
    ) {
        service.registerUser(registerModel).enqueue(object :
            Callback<UserTokenModel> {
            override fun onFailure(call: Call<UserTokenModel>, t: Throwable) {
                onFailureAction()
            }

            override fun onResponse(call: Call<UserTokenModel>, response: Response<UserTokenModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    onBadResponseAction(response.code(), response.errorBody()!!)
                    return
                }
                Common.userToken = response.body()!!.token
                TokenManager.getInstance().saveToken()
                onResponseAction()
            }
        })
    }
}