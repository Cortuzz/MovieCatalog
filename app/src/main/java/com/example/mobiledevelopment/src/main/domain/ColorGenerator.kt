package com.example.mobiledevelopment.src.main.domain

import androidx.compose.ui.graphics.Color
import androidx.core.math.MathUtils
import com.example.mobiledevelopment.include.retrofit.ReviewShortModel
import com.example.mobiledevelopment.ui.theme.AccentColor
import java.lang.Float.NaN
import kotlin.math.roundToInt

class ColorGenerator {
    companion object {
        fun getRating(reviews: List<ReviewShortModel>): Float {
            var averageRating = 0f
            for (review in reviews) {
                averageRating += review.rating
            }
            averageRating /= reviews.size

            return if (averageRating.isNaN()) NaN else (averageRating * 100.0).roundToInt() / 100.0f
        }

        fun getColor(rating: Float): Color {
            if (rating.isNaN())
                return AccentColor

            val clRating = MathUtils.clamp(rating, 0f, 10f)

            val r = MathUtils.clamp(getRedLayer(clRating), 0, 255)
            val g = MathUtils.clamp(getGreenLayer(clRating), 0, 255)
            val b = MathUtils.clamp(getBlueLayer(clRating), 0, 255)
            return Color(r, g, b)
        }

        private fun getRedLayer(rating: Float): Int {
            return 50 * (-rating + 9.25).toInt()
        }

        private fun getGreenLayer(rating: Float): Int {
            if (rating <= 7.2)
                return 10 * (3 * rating - 1).toInt()

            return 5 * (-5 * rating + 78).toInt()
        }

        private fun getBlueLayer(rating: Float): Int {
            if (rating >= 7.2 || rating <= 2.0)
                return 0

            return 15 * (rating - 2).toInt()
        }
    }
}