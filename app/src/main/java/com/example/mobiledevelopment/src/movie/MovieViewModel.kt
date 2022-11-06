package com.example.mobiledevelopment.src.movie

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.mobiledevelopment.src.domain.models.MovieDetailsModel
import com.example.mobiledevelopment.src.domain.models.ReviewModel
import com.example.mobiledevelopment.src.domain.models.ReviewModifyModel
import com.example.mobiledevelopment.src.domain.movie.ReviewCheckerService

class MovieViewModel: ViewModel() {
    private var movieModel: MutableState<MovieDetailsModel?> = mutableStateOf(null)
    private var reviewsModel = mutableStateListOf<ReviewModel>()
    private val repository = MovieRepository()

    private var reviewDialogOpened = mutableStateOf(false)
    private var reviewInputText = mutableStateOf("")
    private var reviewInputRating = mutableStateOf(0)
    private var reviewInputAnonymous = mutableStateOf(false)

    private var isReviewAdded = mutableStateOf(false)
    private val reviewChecker = ReviewCheckerService()

    fun getMovie() {
        reviewsModel = mutableStateListOf()

        repository.getMovie(
            onResponseAction = {
                val movie = it
                isReviewAdded.value = reviewChecker.isReviewsContainsId(
                    repository.getUserId(),
                    movie.reviews
                )
                reviewChecker.placeMyReviewToTop(repository.getUserId(), movie.reviews)
                movieModel.value = movie
                parseMovieModel()
            },
            onFailureAction = {

            }
        )
    }

    private fun parseMovieModel() {
        for (review in movieModel.value?.reviews ?: listOf()) {
            reviewsModel.add(review)
        }
        movieModel.value?.reviews = mutableListOf()
    }

    fun addToFavourites(onUnauthorized: () -> Unit) {
        repository.addMovieToFavourites(
            onFailureAction = {  },
            onBadResponseAction = { if (it == 401) onUnauthorized() },
            onResponseAction = {  }
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

    fun getReviewList(): SnapshotStateList<ReviewModel> {
        return reviewsModel
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
                reviewsModel.removeFirst()
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