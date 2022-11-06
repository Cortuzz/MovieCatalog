package com.example.mobiledevelopment.src.main

import android.util.Log
import com.example.mobiledevelopment.src.domain.models.MovieElementModel
import com.example.mobiledevelopment.src.domain.models.MoviesListModel
import com.example.mobiledevelopment.src.domain.models.MoviesPageListModel
import com.example.mobiledevelopment.src.domain.retrofit.Common
import com.example.mobiledevelopment.src.domain.utils.SharedStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {
    private val service = Common.retrofitService

    fun getMovies(
        page: Int,
        onResponseAction: (Response<MoviesPageListModel>) -> Unit,
        onFailureAction: () -> Unit,
    ) {
        service.getMoviesList(page).enqueue(object : Callback<MoviesPageListModel> {
            override fun onFailure(call: Call<MoviesPageListModel>, t: Throwable) {
                Log.e("Network manager", "Fetching movies failed with exception: $t")
                onFailureAction()
            }

            override fun onResponse(call: Call<MoviesPageListModel>, response: Response<MoviesPageListModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    Log.w("Network manager", "Fetching movies failed with response: ${response.errorBody()?.charStream()?.readText()}")
                    onFailureAction()
                    return
                }

                Log.i("Network manager", "Movies fetched successfully. Response: ${response.body()}")
                onResponseAction(response)
            }
        })
    }

    fun getFavouriteMovies(
        onResponseAction: (Response<MoviesListModel>) -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int) -> Unit
    ) {
        service.getFavouritesMovies("Bearer ${SharedStorage.userToken}").enqueue(object : Callback<MoviesListModel> {
            override fun onFailure(call: Call<MoviesListModel>, t: Throwable) {
                onFailureAction()
                Log.e("Network manager", "Fetching favourites movies failed with exception: $t")
            }

            override fun onResponse(call: Call<MoviesListModel>, response: Response<MoviesListModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    Log.w("Network manager", "Fetching favourites movies failed with code ${response.code()} and response: ${response.errorBody()?.charStream()?.readText()}")
                    onBadResponseAction(response.code())
                    return
                }

                Log.i("Network manager", "Favourites movies fetched successfully. Code: ${response.code()} Response: ${response.body()}")
                onResponseAction(response)
            }
        })
    }

    fun setCurrentMovie(movieElementModel: MovieElementModel) {
        SharedStorage.currentMovieId = movieElementModel.id
    }

    fun addMovieToFavourites(
        id: String,
        onResponseAction: (Response<Void>) -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int) -> Unit
    ) {
        service.addToFavourites("Bearer ${SharedStorage.userToken}", id).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                onFailureAction()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    onBadResponseAction(response.code())
                    return
                }

                onResponseAction(response)
            }
        })
    }

    fun removeMovieFromFavourites(
        id: String,
        onResponseAction: (Response<Void>) -> Unit,
        onBadResponseAction: (Int) -> Unit,
        onFailureAction: () -> Unit,
    ) {
        service.removeFromFavourites("Bearer ${SharedStorage.userToken}", id).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Network manager", "Removing movie from favourites failed with exception: $t")
                onFailureAction()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    Log.w("Network manager", "Removing movie from favourites failed with code ${response.code()} and response: ${response.errorBody()?.charStream()?.readText()}")
                    onBadResponseAction(response.code())
                    return
                }

                Log.i("Network manager", "Movie removed from favourites successfully. Code: ${response.code()} Response: ${response.body()}")
                onResponseAction(response)
            }
        })
    }
}