package com.example.mobiledevelopment.src.login

import android.util.Log
import com.example.mobiledevelopment.src.domain.retrofit.Common
import com.example.mobiledevelopment.src.domain.utils.TokenManager
import com.example.mobiledevelopment.src.domain.models.UserLoginModel
import com.example.mobiledevelopment.src.domain.models.UserTokenModel
import com.example.mobiledevelopment.src.domain.utils.SharedStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {
    private val service = Common.retrofitService

    fun tryLoginUser(
        loginModel: UserLoginModel,
        onResponseAction: () -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: () -> Unit,
    ) {
        service.loginUser(loginModel).enqueue(object : Callback<UserTokenModel> {
            override fun onFailure(call: Call<UserTokenModel>, t: Throwable) {
                onFailureAction()
                Log.e("Network manager", "Login failed with exception: $t")
            }

            override fun onResponse(call: Call<UserTokenModel>, response: Response<UserTokenModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    onBadResponseAction()
                    Log.w("Network manager", "Login failed with response: ${response.errorBody()?.charStream()?.readText()}")
                    return
                }
                Log.i("Network manager", "Login successful. Response: ${response.body()}")
                SharedStorage.userToken = response.body()!!.token
                TokenManager.getInstance().saveToken()
                onResponseAction()
            }
        })
    }
}