package com.example.mobiledevelopment.src.main

import com.example.mobiledevelopment.include.retrofit.Common
import com.example.mobiledevelopment.include.retrofit.MovieElementModel
import com.example.mobiledevelopment.include.retrofit.MoviesListModel
import com.example.mobiledevelopment.include.retrofit.MoviesPageListModel
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

    fun test() {
        service.addToFavourites("Bearer ${Common.userToken}", "82c34463-daf4-4702-a2b8-08d9b9f3d2a2").enqueue(object : Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                println()
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (!response.isSuccessful || response.body() == null) {
                    return
                }

                println(response.body())
            }
        })
    }
}