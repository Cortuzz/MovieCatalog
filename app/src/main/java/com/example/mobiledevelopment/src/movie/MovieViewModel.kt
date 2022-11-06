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

    private val isInFavourites = mutableStateOf(false)
    private var isReviewAdded = mutableStateOf(false)
    private var myReviewId = mutableStateOf("")
    private val reviewChecker = ReviewCheckerService()

    fun getMovie(updateModel: Boolean = true) {
        isInFavourites.value = false
        repository.isInFavourites { isInFavourites.value = true }

        repository.getMovie(
            onResponseAction = {
                val movie = it
                val reviewId = reviewChecker.isReviewsContainsId(
                    repository.getUserId(),
                    movie.reviews
                )
                isReviewAdded.value = !reviewId.isNullOrEmpty()
                myReviewId.value = reviewId ?: ""

                reviewChecker.placeMyReviewToTop(repository.getUserId(), movie.reviews)
                if (updateModel) movieModel.value = movie

                parseMovieModel(movie.reviews)
            },
            onFailureAction = {

            }
        )
    }

    private fun parseMovieModel(reviews: List<ReviewModel>) {
        reviewsModel.clear()

        for (review in reviews) {
            reviewsModel.add(review)
        }
        movieModel.value?.reviews = mutableListOf()
    }

    fun changeFavouritesStatus(onUnauthorized: () -> Unit) {
        if (isInFavourites.value) {
            removeFromFavourites(onUnauthorized)
            return
        }
        addToFavourites(onUnauthorized)
    }

    private fun removeFromFavourites(onUnauthorized: () -> Unit) {
        repository.removeMovieFromFavourites(
            onFailureAction = {  },
            onBadResponseAction = { if (it == 401) onUnauthorized() },
            onResponseAction = { isInFavourites.value = false }
        )
    }

    private fun addToFavourites(onUnauthorized: () -> Unit) {
        repository.addMovieToFavourites(
            onFailureAction = {  },
            onBadResponseAction = { if (it == 401) onUnauthorized() },
            onResponseAction = { isInFavourites.value = true }
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

    fun getFavouriteStatus(): MutableState<Boolean> {
        return isInFavourites
    }

    fun sendReview(onUnauthorized: () -> Unit) {
        val reviewModel = ReviewModifyModel(
            reviewText = reviewInputText.value,
            rating = reviewInputRating.value,
            isAnonymous = reviewInputAnonymous.value
        )

        if (movieModel.value == null) return
        if (isReviewAdded.value) {
            editReview(reviewModel, onUnauthorized)
            return
        }

        repository.sendReview(
            movieModel.value!!.id,
            reviewModel,
            onResponseAction = {
                getMovie(false)
                isReviewAdded.value = true
                clearReview()
            },
            onBadResponseAction = { if (it == 401) onUnauthorized() },
            onFailureAction = { }
        )
    }

    private fun editReview(model: ReviewModifyModel, onUnauthorized: () -> Unit) {
        repository.editReview(
            myReviewId.value,
            model,
            onResponseAction = {
                getMovie(false)
                isReviewAdded.value = true
                clearReview()
            },
            onBadResponseAction = {
                if (it == 401) onUnauthorized()
            },
            onFailureAction = { }
        )
    }

    fun deleteReview(id: String, onUnauthorized: () -> Unit) {
        if (movieModel.value == null) return

        repository.deleteReview(
            id,
            onResponseAction = {
                reviewsModel.removeFirst()
                isReviewAdded.value = false
                clearReview()
            },
            onBadResponseAction = { if (it == 401) onUnauthorized() },
            onFailureAction = { }
        )
    }

    fun openEditDialog(review: ReviewModel) {
        reviewDialogOpened.value = true
        reviewInputText.value = review.reviewText ?: ""
        reviewInputRating.value = review.rating
        reviewInputAnonymous.value = review.isAnonymous
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