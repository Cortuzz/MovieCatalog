package com.example.mobiledevelopment.src.movie

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mobiledevelopment.include.retrofit.MovieDetailsModel
import com.example.mobiledevelopment.include.retrofit.ReviewModifyModel

class MovieViewModel {
    private var movieModel: MutableState<MovieDetailsModel?> = mutableStateOf(null)
    private val repository = MovieRepository()
    private var reviewInputText = mutableStateOf("")
    private var reviewInputRating = mutableStateOf(0)
    private var reviewInputAnonymous = mutableStateOf(false)

    fun getMovie() {
        repository.getMovie(
            onResponseAction = {
                movieModel.value = it.body()
            },
            onFailureAction = {

            }
        )
    }

    fun getReviewInputText(): MutableState<String> {
        return reviewInputText
    }

    fun getReviewInputRating(): MutableState<Int> {
        return reviewInputRating
    }

    fun getReviewInputAnonymous(): MutableState<Boolean> {
        return reviewInputAnonymous
    }

    fun sendReview(onUnauthorized: () -> Unit) {
        val reviewModel = ReviewModifyModel(
            reviewText = reviewInputText.value,
            rating = reviewInputRating.value,
            isAnonymous = reviewInputAnonymous.value
        )

        if (movieModel.value == null) return
        repository.sendReview(
            movieModel.value!!.id,
            reviewModel,
            onResponseAction = {getMovie()},
            onBadResponseAction = { if (it == 401) onUnauthorized() },
            onFailureAction = {}
        )
    }

    fun clearReview() {
        reviewInputRating.value = 0
        reviewInputText.value = ""
        reviewInputAnonymous.value = false
    }

    fun getMovieModel(): MutableState<MovieDetailsModel?> {
        return movieModel
    }

    fun compareIds(id: String): Boolean {
        return id == repository.getUserId()
    }
}