package com.example.mobiledevelopment.src.domain.movie

import com.example.mobiledevelopment.src.domain.retrofit.ReviewModel

class ReviewCheckerService {
    fun isReviewsContainsId(id: String, reviews: List<ReviewModel>?): Boolean {
        if (reviews.isNullOrEmpty()) return false

        for (review in reviews) {
            if (review.author?.userId != id)
                continue

            review.isMine = true
            return true
        }

        return false
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