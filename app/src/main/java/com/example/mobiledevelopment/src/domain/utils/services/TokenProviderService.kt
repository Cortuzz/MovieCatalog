package com.example.mobiledevelopment.src.domain.utils.services

import androidx.activity.ComponentActivity
import com.example.mobiledevelopment.src.domain.retrofit.Common
import com.example.mobiledevelopment.src.domain.composes.dropAllStates
import com.example.mobiledevelopment.src.domain.models.ProfileModel
import com.example.mobiledevelopment.src.domain.utils.SharedStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class TokenProviderService(private val activity: ComponentActivity) {
    companion object {
        private const val TOKEN_NAME = "auth.token"
        private var instance: TokenProviderService? = null

        fun getInstance(activity: ComponentActivity): TokenProviderService {
            if (instance == null)
                instance = TokenProviderService(activity)

            return instance as TokenProviderService
        }

        fun getInstance(): TokenProviderService {
            if (instance == null)
                throw IllegalStateException("The initial initialization must belong to the activity")

            return instance as TokenProviderService
        }
    }
    private val service = Common.retrofitService

    fun checkToken(
        onFailureAction: () -> Unit = {  },
        onSuccessAction: (model: ProfileModel) -> Unit
    ) {
        if (SharedStorage.userToken.isEmpty()) {
            onFailureAction()
            return
        }

        service.getProfile("Bearer ${SharedStorage.userToken}").enqueue(object :
            Callback<ProfileModel> {
            override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                onFailureAction()
            }

            override fun onResponse(call: Call<ProfileModel>, response: Response<ProfileModel>) {
                if (response.code() == 401) {
                    onFailureAction()
                    return
                }

                SharedStorage.userId = response.body()?.id ?: ""
                response.body()?.let { onSuccessAction(it) }
            }
        })
    }

    fun checkTokenSynchronously(): Boolean {
        if (SharedStorage.userToken.isEmpty())
            return false

        try {
            val callback = service.getProfile("Bearer ${SharedStorage.userToken}").execute()
            if (!callback.isSuccessful)
                return false

            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun dropToken() {
        SharedStorage.userId = ""
        SharedStorage.userToken = ""
        saveToken()
        dropAllStates()
    }

    fun saveToken() {
        checkToken {  }
        val file = File(activity.filesDir, TOKEN_NAME)
        file.setWritable(true)
        file.writeText(SharedStorage.userToken)
    }

    fun loadToken() {
        val files = activity.filesDir.listFiles() ?: return

        for (file in files) {
            if (file.name == TOKEN_NAME) {
                SharedStorage.userToken = file.readText()
                break
            }
        }
    }
}