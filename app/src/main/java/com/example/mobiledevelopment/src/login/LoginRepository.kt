package com.example.mobiledevelopment.src.login

import com.example.mobiledevelopment.include.retrofit.Common
import com.example.mobiledevelopment.include.retrofit.UserLoginModel
import com.example.mobiledevelopment.include.retrofit.UserTokenModel
import com.example.mobiledevelopment.src.TokenManager
import com.example.mobiledevelopment.src.login.domain.ViewField
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {
    private val service = Common.retrofitService

    fun tryLoginUser(
        fields: MutableMap<ViewField, String>,
        onResponseAction: () -> Unit,
        onFailureAction: () -> Unit,
    ) {
        val loginModel = UserLoginModel(
            username = fields[ViewField.Login]!!,
            password = fields[ViewField.Password]!!,
        )

        service.loginUser(loginModel).enqueue(object : Callback<UserTokenModel> {
            override fun onFailure(call: Call<UserTokenModel>, t: Throwable) {
                onFailureAction()
            }

            override fun onResponse(call: Call<UserTokenModel>, response: Response<UserTokenModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    onFailureAction()
                    return
                }
                Common.userToken = response.body()!!.token
                TokenManager.getInstance().saveToken()
                onResponseAction()
            }
        })
    }
}