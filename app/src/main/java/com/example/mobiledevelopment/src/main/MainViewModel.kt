package com.example.mobiledevelopment.src.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.mobiledevelopment.src.domain.models.MovieElementModel
import com.example.mobiledevelopment.src.domain.models.MoviesPageListModel

class MainViewModel {
    private val repository: MainRepository = MainRepository()
    private var promotedMovie: MutableState<MovieElementModel?> = mutableStateOf(null)
    private var movieList = mutableStateListOf<MovieElementModel>()
    private var favouriteMovieList = mutableStateListOf<MovieElementModel>()
    private var pageObtained = mutableStateOf(1)

    var totalPages = mutableStateOf(-1)
    var isMovieFetched = mutableStateOf(false)

    init {
       init { }
    }

    private fun init(onUnauthorized: () -> Unit) {
        totalPages.value = -1
        isMovieFetched.value = false

        repository.getFavouriteMovies(
            onResponseAction = {
                val moviesPageModel = it
                val movies = moviesPageModel.movies

                for (movie in movies) {
                    favouriteMovieList.add(movie)
                }
                isMovieFetched.value = true
            },
            onBadResponseAction = {
                if (it == 401) onUnauthorized()
            },
            onFailureAction = {

            }
        )
    }

    fun refresh(onUnauthorized: () -> Unit) {
        pageObtained.value = 1
        favouriteMovieList.clear()
        movieList.clear()
        init(onUnauthorized)
    }

    private fun parseMoviesModel(moviesPageModel: MoviesPageListModel) {
        val movies = (moviesPageModel.movies).toMutableList()
        totalPages.value = moviesPageModel.pageInfo.pageCount

        if (pageObtained.value == 1) {
            promotedMovie.value = movies.removeFirst()
        }

        for (movie in movies) {
            movieList.add(movie)
        }
    }

    fun fetchNextPage() {
        repository.getMovies(pageObtained.value,
            onResponseAction = {
                parseMoviesModel(it)
                pageObtained.value++
            },
            onFailureAction = {
                // todo: throw Exception()
            }
        )
    }

    fun openMovie(movieElementModel: MovieElementModel) {
        repository.setCurrentMovie(movieElementModel)
    }

    fun removeFromFavourites(movieElementModel: MovieElementModel, onUnauthorized: () -> Unit) {
        val movieId = movieElementModel.id

        repository.removeMovieFromFavourites(
            id = movieId,
            onFailureAction = { },
            onBadResponseAction =  { if (it == 401) onUnauthorized() },
            onResponseAction = { favouriteMovieList.remove(movieElementModel) }
        )
    }

    fun getCurrentPage(): MutableState<Int> {
        return pageObtained
    }

    fun getMovieList(): SnapshotStateList<MovieElementModel> {
        return movieList
    }

    fun getPromotedMovie(): MutableState<MovieElementModel?> {
        return promotedMovie
    }

    fun getFavouriteMovieList(): SnapshotStateList<MovieElementModel> {
        return favouriteMovieList
    }
}