package com.example.mobiledevelopment.src.movie

import com.example.mobiledevelopment.include.retrofit.*
import com.example.mobiledevelopment.src.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository {
    private val service = Common.retrofitService
    private lateinit var userModel: ProfileModel

    init {
        TokenManager.getInstance().checkToken(
            onFailureAction = {
                TokenManager.getInstance().baseOnFailureAction()
            },
            onSuccessAction = {
                userModel = it
            }
        )
    }

    fun getMovie(
        onResponseAction: (Response<MovieDetailsModel>) -> Unit,
        onFailureAction: () -> Unit,
    ) {
        service.getMovie(Common.currentMovieId).enqueue(object : Callback<MovieDetailsModel> {
            override fun onFailure(call: Call<MovieDetailsModel>, t: Throwable) {
                onFailureAction()
            }

            override fun onResponse(call: Call<MovieDetailsModel>, response: Response<MovieDetailsModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    onFailureAction()
                    return
                }

                onResponseAction(response)
            }
        })
    }

    fun sendReview(
        id: String,
        reviewModifyModel: ReviewModifyModel,
        onResponseAction: () -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int) -> Unit
    ) {
        service.addReview( "Bearer ${Common.userToken}",id, reviewModifyModel).enqueue(object : Callback<Void> {
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

    fun getUserId(): String {
        return userModel.id
    }
}