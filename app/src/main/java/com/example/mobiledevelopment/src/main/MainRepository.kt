package com.example.mobiledevelopment.src.main

import com.example.mobiledevelopment.include.retrofit.*
import com.example.mobiledevelopment.src.TokenManager
import com.google.gson.JsonElement
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
                onFailureAction()
            }

            override fun onResponse(call: Call<MoviesPageListModel>, response: Response<MoviesPageListModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    onFailureAction()
                    return
                }

                onResponseAction(response)
            }
        })
    }

    fun getFavouriteMovies(
        onResponseAction: (Response<MoviesListModel>) -> Unit,
        onFailureAction: () -> Unit,
    ) {
        service.getFavouritesMovies("Bearer ${Common.userToken}").enqueue(object : Callback<MoviesListModel> {
            override fun onFailure(call: Call<MoviesListModel>, t: Throwable) {
                onFailureAction()
            }

            override fun onResponse(call: Call<MoviesListModel>, response: Response<MoviesListModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    onFailureAction()
                    return
                }

                onResponseAction(response)
            }
        })
    }

    fun setCurrentMovie(movieElementModel: MovieElementModel) {
        Common.currentMovieId = movieElementModel.id
    }

    fun addMovieToFavourites(
        id: String,
        onResponseAction: (Response<Void>) -> Unit,
        onFailureAction: () -> Unit,
    ) {
        service.addToFavourites("Bearer ${Common.userToken}", id).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                onFailureAction()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    onFailureAction()
                    return
                }

                onResponseAction(response)
            }
        })
    }

    fun removeMovieFromFavourites(
        id: String,
        onResponseAction: (Response<Void>) -> Unit,
        onFailureAction: () -> Unit,
    ) {
        service.removeFromFavourites("Bearer ${Common.userToken}", id).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                onFailureAction()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    onFailureAction()
                    return
                }

                onResponseAction(response)
            }
        })
    }
}