package com.example.mobiledevelopment.src.admin

import androidx.compose.runtime.mutableStateOf
import com.example.mobiledevelopment.src.domain.admin.*
import com.example.mobiledevelopment.src.domain.models.GenreModel
import com.example.mobiledevelopment.src.domain.models.GenresModel
import com.example.mobiledevelopment.src.domain.models.InsertMovieModel

class AdminViewModel {
    private val repository = AdminRepository()
    var fields = ViewFieldsProvider()

    init {
        refresh()
    }

    private fun refresh() {
        fields.refresh()
        repository.getGenres(
            onFailureAction = { },
            onResponseAction = { genres -> parseGenres(genres) }
        )
    }

    private fun parseGenres(genres: GenresModel) {
        for (genre in genres.genres) {
            fields.availableGenres.add(genre)
        }
    }

    fun addMovie() {
        repository.addMovie(
            model = fields.getInsertModel(),
            onResponseAction = { refresh() },
            onFailureAction = { }
        )
    }
}