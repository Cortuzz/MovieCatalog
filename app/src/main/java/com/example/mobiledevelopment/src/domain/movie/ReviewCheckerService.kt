package com.example.mobiledevelopment.src.domain.movie

import com.example.mobiledevelopment.src.domain.models.ReviewModel


class ReviewCheckerService {
    fun isReviewsContainsId(id: String, reviews: List<ReviewModel>?): String? {
        if (reviews.isNullOrEmpty()) return null

        for (review in reviews) {
            if (review.author?.userId != id)
                continue

            review.isMine = true
            return review.id
        }

        return null
    }

    fun placeMyReviewToTop(id: String, reviews: MutableList<ReviewModel>?) {
        if (reviews.isNullOrEmpty()) return

        for (review in reviews) {
            if (review.author?.userId != id)
                continue

            reviews.remove(review)
            reviews.add(0, review)
            return
        }
    }
}