package com.example.mobiledevelopment.src.movie

import android.util.Log
import com.example.mobiledevelopment.src.domain.models.MovieDetailsModel
import com.example.mobiledevelopment.src.domain.models.ReviewModifyModel
import com.example.mobiledevelopment.src.domain.retrofit.Common
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
                Log.e("Network manager", "Fetching movie failed with exception: $t")
                onFailureAction()
            }

            override fun onResponse(call: Call<MovieDetailsModel>, response: Response<MovieDetailsModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    Log.w("Network manager", "Fetching movies failed with response: ${response.errorBody()?.charStream()?.readText()}")
                    onFailureAction()
                    return
                }

                Log.i("Network manager", "Movie successfully fetched. Response: ${response.body()}")
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
                Log.e("Network manager", "Sending review failed with exception: $t")
                onFailureAction()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    Log.w("Network manager", "Sending review failed with response: ${response.errorBody()?.charStream()?.readText()}")
                    onBadResponseAction(response.code())
                    return
                }

                Log.i("Network manager", "Review successfully sent. Response: ${response.body()}")
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
                Log.e("Network manager", "Deleting review failed with exception: $t")
                onFailureAction()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    Log.w("Network manager", "Deleting review failed with response: ${response.errorBody()?.charStream()?.readText()}")
                    onBadResponseAction(response.code())
                    return
                }

                Log.i("Network manager", "Review successfully deleted. Response: ${response.body()}")
                onResponseAction()
            }
        })
    }

    fun getUserId(): String {
        return Common.userId
    }
}