package com.example.mobiledevelopment.src.movie

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mobiledevelopment.include.retrofit.MovieDetailsModel
import com.example.mobiledevelopment.include.retrofit.ReviewModifyModel
import com.example.mobiledevelopment.src.movie.domain.ReviewCheckerService

class MovieViewModel {
    private var movieModel: MutableState<MovieDetailsModel?> = mutableStateOf(null)
    private val repository = MovieRepository()
    private var reviewDialogOpened = mutableStateOf(false)
    private var reviewInputText = mutableStateOf("")
    private var reviewInputRating = mutableStateOf(0)
    private var reviewInputAnonymous = mutableStateOf(false)
    private var isReviewAdded = mutableStateOf(false)
    private val reviewChecker = ReviewCheckerService()

    fun getMovie() {
        repository.getMovie(
            onResponseAction = {
                val movie = it.body()
                isReviewAdded.value = reviewChecker.isReviewsContainsId(
                    repository.getUserId(),
                    movie?.reviews
                )
                reviewChecker.placeMyReviewToTop(repository.getUserId(), movie?.reviews)
                movieModel.value = movie
            },
            onFailureAction = {

            }
        )
    }

    fun getReviewDialogState(): MutableState<Boolean> {
        return reviewDialogOpened
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
            onResponseAction = {
                getMovie()
                isReviewAdded.value = true
            },
            onBadResponseAction = { if (it == 401) onUnauthorized() },
            onFailureAction = { }
        )
    }

    fun deleteReview(id: String, onUnauthorized: () -> Unit) {
        if (movieModel.value == null) return

        repository.deleteReview(
            id,
            onResponseAction = {
                movieModel.value?.reviews?.removeFirst()
            },
            onBadResponseAction = { if (it == 401) onUnauthorized() },
            onFailureAction = { }
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

    fun getClientReviewState(): MutableState<Boolean> {
        return isReviewAdded
    }
}