package com.example.mobiledevelopment.src.domain.utils

import com.example.mobiledevelopment.src.domain.models.GenreModel
import java.util.*

class Utils {
    companion object {
        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun parseMoney(value: Int?): String? {
            if (value == null) return null
            return "$" + String.format(Locale.US, "%,d", value).replace(",", " ")
        }

        fun parseGenres(genres: List<GenreModel>): String {
            var parsedGenres = ""
            for (genre in genres) {
                parsedGenres += "${genre.name}, "
            }
            parsedGenres = parsedGenres.dropLast(2)
            return parsedGenres
        }
    }
}