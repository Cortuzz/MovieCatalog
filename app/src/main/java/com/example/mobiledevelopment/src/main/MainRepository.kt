package com.example.mobiledevelopment.src.main

import android.util.Log
import com.example.mobiledevelopment.src.domain.models.MovieElementModel
import com.example.mobiledevelopment.src.domain.models.MoviesListModel
import com.example.mobiledevelopment.src.domain.models.MoviesPageListModel
import com.example.mobiledevelopment.src.domain.retrofit.Common
import com.example.mobiledevelopment.src.domain.utils.SharedStorage
import com.example.mobiledevelopment.src.domain.utils.services.RequestsProviderService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {
    private val service = RequestsProviderService()

    fun getMovies(
        page: Int,
        onResponseAction: (MoviesPageListModel) -> Unit,
        onFailureAction: () -> Unit,
    ) {
       service.getMovies(
           page = page,
           onResponseAction = { _, body -> onResponseAction(body) },
           onFailureAction = { onFailureAction() },
           onBadResponseAction = { _, _ -> onFailureAction() }
       )
    }

    fun getFavouriteMovies(
        onResponseAction: (MoviesListModel) -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int) -> Unit
    ) {
        service.getFavouriteMovies(
            onResponseAction = { _, body -> onResponseAction(body) },
            onFailureAction = { onFailureAction() },
            onBadResponseAction = { code, _ -> onBadResponseAction(code) }
        )
    }

    fun setCurrentMovie(movieElementModel: MovieElementModel) {
        SharedStorage.currentMovieId = movieElementModel.id
    }

    fun removeMovieFromFavourites(
        id: String,
        onResponseAction: () -> Unit,
        onBadResponseAction: (Int) -> Unit,
        onFailureAction: () -> Unit,
    ) {
        service.removeMovieFromFavourites(
            id = id,
            onResponseAction = { onResponseAction() },
            onBadResponseAction = { code, _ -> onBadResponseAction(code) },
            onFailureAction = { onFailureAction() }
        )
    }
}