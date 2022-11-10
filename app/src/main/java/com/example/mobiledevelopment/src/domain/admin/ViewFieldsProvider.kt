package com.example.mobiledevelopment.src.domain.admin

import com.example.mobiledevelopment.src.domain.models.GenreModel
import com.example.mobiledevelopment.src.domain.models.InsertMovieModel

class ViewFieldsProvider {
    var fields = listOf<ViewFieldProvider>()
    var availableGenres = mutableListOf<GenreModel>()

    init {
        refresh()
    }

    fun refresh() {
        fields = listOf(
            ViewFieldProvider(NameText),
            ViewFieldProvider(PosterText),
            ViewFieldProvider(YearText, true),
            ViewFieldProvider(CountryText),
            ViewFieldProvider(TimeText, true),
            ViewFieldProvider(TaglineText),
            ViewFieldProvider(DescriptionText),
            ViewFieldProvider(DirectorText),
            ViewFieldProvider(BudgetText, true),
            ViewFieldProvider(FeesText, true),
            ViewFieldProvider(AgeRestrictionText, true)
        )
        availableGenres.clear()
    }

    fun getInsertModel(): InsertMovieModel {
        return InsertMovieModel(
            name = fields[0].value.value,
            poster = fields[1].value.value,
            year = fields[2].value.value.toInt(),
            country = fields[3].value.value,
            time = fields[4].value.value.toInt(),
            tagline = fields[5].value.value,
            description = fields[6].value.value,
            director = fields[7].value.value,
            budget = fields[8].value.value.toInt(),
            fees = fields[9].value.value.toInt(),
            ageLimit = fields[10].value.value.toInt(),
            genres = parseGenres()
        )
    }

    private fun parseGenres(): List<GenreModel> {
        val parsed = mutableListOf<GenreModel>()

        for (genre in availableGenres) {
            if (genre.isActive) parsed.add(genre)
        }

        return parsed
    }
}