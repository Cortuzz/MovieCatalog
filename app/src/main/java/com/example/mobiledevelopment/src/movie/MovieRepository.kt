package com.example.mobiledevelopment.src.movie

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mobiledevelopment.include.retrofit.*
import com.example.mobiledevelopment.src.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository {
    private val service = Common.retrofitService

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

    fun deleteReview(
        id: String,
        onResponseAction: () -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int) -> Unit
    ) {
        service.deleteReview( "Bearer ${Common.userToken}", Common.currentMovieId, id).enqueue(object : Callback<Void> {
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
        return Common.userId
    }
}