package com.example.mobiledevelopment.src

import com.example.mobiledevelopment.include.retrofit.Common
import com.example.mobiledevelopment.include.retrofit.ProfileModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class TokenManager(private val activity: MainActivity) {
    companion object {
        private const val TOKEN_NAME = "auth.token"
        private var instance: TokenManager? = null

        fun getInstance(activity: MainActivity): TokenManager {
            if (instance == null)
                instance = TokenManager(activity)

            return instance as TokenManager
        }

        fun getInstance(): TokenManager {
            if (instance == null)
                throw IllegalStateException("The initial initialization must belong to the activity")

            return instance as TokenManager
        }
    }
    private val service = Common.retrofitService

    fun checkToken(
        onFailureAction: () -> Unit = { baseOnFailureAction() },
        onSuccessAction: (model: ProfileModel) -> Unit
    ) {
        if (Common.userToken.isEmpty()) {
            onFailureAction()
            return
        }

        service.getProfile("Bearer ${Common.userToken}").enqueue(object :
            Callback<ProfileModel> {
            override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                onFailureAction()
            }

            override fun onResponse(call: Call<ProfileModel>, response: Response<ProfileModel>) {
                if (response.code() == 401) {
                    onFailureAction()
                    return
                }
                response.body()?.let { onSuccessAction(it) }
            }
        })
    }


    fun saveToken() {
        val file = File(activity.filesDir, TOKEN_NAME)
        file.setWritable(true)
        file.writeText(Common.userToken)
    }

    fun loadToken() {
        val files = activity.filesDir.listFiles() ?: return

        for (file in files) {
            if (file.name == TOKEN_NAME) {
                Common.userToken = file.readText()
                break
            }
        }
    }

    fun baseOnFailureAction() {

    }
}