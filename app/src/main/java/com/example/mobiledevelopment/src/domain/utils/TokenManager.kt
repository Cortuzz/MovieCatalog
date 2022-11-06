package com.example.mobiledevelopment.src.domain.utils

import com.example.mobiledevelopment.src.domain.retrofit.Common
import com.example.mobiledevelopment.src.Application
import com.example.mobiledevelopment.src.domain.composes.dropAllStates
import com.example.mobiledevelopment.src.domain.models.ProfileModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class TokenManager(private val activity: Application) {
    companion object {
        private const val TOKEN_NAME = "auth.token"
        private var instance: TokenManager? = null

        fun getInstance(activity: Application): TokenManager {
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
        onFailureAction: () -> Unit = {  },
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

                Common.userId = response.body()?.id ?: ""
                response.body()?.let { onSuccessAction(it) }
            }
        })
    }

    fun dropToken() {
        Common.userId = ""
        Common.userToken = ""
        saveToken()
        dropAllStates()
    }

    fun saveToken() {
        checkToken {  }
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
}