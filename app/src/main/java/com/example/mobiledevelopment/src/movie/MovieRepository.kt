package com.example.mobiledevelopment.src.movie

import com.example.mobiledevelopment.src.domain.models.MovieDetailsModel
import com.example.mobiledevelopment.src.domain.models.ReviewModifyModel
import com.example.mobiledevelopment.src.domain.utils.SharedStorage
import com.example.mobiledevelopment.src.domain.utils.services.RequestsProviderService

class MovieRepository {
    private val service = RequestsProviderService()

    fun getMovie(
        onResponseAction: (MovieDetailsModel) -> Unit,
        onFailureAction: () -> Unit,
    ) {
        service.getMovie(
            onFailureAction = { onFailureAction() },
            onResponseAction = { _, body -> onResponseAction(body) },
            onBadResponseAction = { _, _ -> onFailureAction() }
        )
    }

    fun addMovieToFavourites(
        onResponseAction: (Int) -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int) -> Unit
    ) {
        service.addMovieToFavourites(
            id = SharedStorage.currentMovieId,
            onBadResponseAction = { code, _ -> onBadResponseAction(code) },
            onFailureAction = { onFailureAction() },
            onResponseAction = { code -> onResponseAction(code) }
        )
    }

    fun sendReview(
        id: String,
        reviewModifyModel: ReviewModifyModel,
        onResponseAction: () -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int) -> Unit
    ) {
        service.sendReview(
            id = id,
            reviewModifyModel = reviewModifyModel,
            onBadResponseAction = { code, _ -> onBadResponseAction(code) },
            onFailureAction = { onFailureAction() },
            onResponseAction = { onResponseAction() }
        )
    }

    fun deleteReview(
        id: String,
        onResponseAction: () -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int) -> Unit
    ) {
        service.deleteReview(
            id = id,
            onResponseAction = { onResponseAction() },
            onFailureAction = { onFailureAction() },
            onBadResponseAction = { code, _ -> onBadResponseAction(code) }
        )
    }

    fun getUserId(): String {
        return SharedStorage.userId
    }
}