package com.example.mobiledevelopment.src.registration

import android.util.Log
import com.example.mobiledevelopment.src.domain.retrofit.Common
import com.example.mobiledevelopment.src.domain.retrofit.UserRegisterModel
import com.example.mobiledevelopment.src.domain.retrofit.UserTokenModel
import com.example.mobiledevelopment.src.domain.TokenManager
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
                Log.e("Network manager", "Registration failed with exception: $t")
            }

            override fun onResponse(call: Call<UserTokenModel>, response: Response<UserTokenModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    onBadResponseAction(response.code(), response.errorBody()!!)
                    Log.w("Network manager", "Registration failed with response: ${response.errorBody()?.charStream()?.readText()}")
                    return
                }
                Log.i("Network manager", "Registration successful. Response: ${response.body()}")
                Common.userToken = response.body()!!.token
                TokenManager.getInstance().saveToken()
                onResponseAction()
            }
        })
    }
}