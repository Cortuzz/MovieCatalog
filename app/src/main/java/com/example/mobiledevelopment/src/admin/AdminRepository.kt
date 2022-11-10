package com.example.mobiledevelopment.src.admin

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.mobiledevelopment.src.domain.admin.ViewFieldProvider
import com.example.mobiledevelopment.src.domain.models.GenreModel
import com.example.mobiledevelopment.src.domain.models.GenresModel
import com.example.mobiledevelopment.src.domain.models.InsertMovieModel
import com.example.mobiledevelopment.src.domain.models.MoviesPageListModel
import com.example.mobiledevelopment.src.domain.utils.services.RequestsProviderService

class AdminRepository {
    private val service = RequestsProviderService()

    fun getGenres(
        onResponseAction: (GenresModel) -> Unit,
        onFailureAction: () -> Unit,
    ) {
        service.getGenres(
            onResponseAction = { _, body -> onResponseAction(body) },
            onFailureAction = { onFailureAction() },
            onBadResponseAction = { _, _ -> onFailureAction() }
        )
    }

    fun addMovie(
        model: InsertMovieModel,
        onResponseAction: () -> Unit,
        onFailureAction: () -> Unit,
    ) {
        try {
            service.addMovie(
                model,
                onResponseAction = { onResponseAction() },
                onFailureAction = { onFailureAction() },
                onBadResponseAction = { _, _ -> onFailureAction() }
            )
        } catch (e: Exception) {
            onFailureAction()
        }
    }
}