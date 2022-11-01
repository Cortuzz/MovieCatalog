package com.example.mobiledevelopment.src.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.mobiledevelopment.include.retrofit.MovieElementModel
import com.example.mobiledevelopment.include.retrofit.MoviesListModel
import com.example.mobiledevelopment.include.retrofit.MoviesPageListModel

class MainViewModel {
    private val repository: MainRepository = MainRepository()
    private var promotedMovie: MutableState<MovieElementModel?> = mutableStateOf(null)
    private var movieList = mutableStateListOf<MovieElementModel>()
    private var favouriteMovieList = mutableStateListOf<MovieElementModel>()
    private var pageObtained = 1

    init {
       init()
    }

    private fun init() {
        repository.getFavouriteMovies(
            onResponseAction = {
                val moviesPageModel = it.body() as MoviesListModel
                val movies = moviesPageModel.movies

                for (movie in movies) {
                    favouriteMovieList.add(movie)
                }
            },
            onFailureAction = {

            }
        )
    }

    fun refresh() {
        pageObtained = 1
        favouriteMovieList.clear()
        movieList.clear()
        init()
    }

    private fun parseMoviesModel(moviesPageModel: MoviesPageListModel, addToPromoted: Boolean = false) {
        val movies = (moviesPageModel.movies).toMutableList()

        if (addToPromoted) {
            promotedMovie.value = movies.removeFirst()
        }

        for (movie in movies) {
            movieList.add(movie)
        }
    }

    fun fetchNextPage() {
        repository.getMovies(pageObtained,
            onResponseAction = {
                parseMoviesModel(it.body() as MoviesPageListModel, pageObtained == 1)
                pageObtained++
            },
            onFailureAction = {
                // todo: throw Exception()
            }
        )
    }

    fun openMovie(movieElementModel: MovieElementModel) {
        repository.setCurrentMovie(movieElementModel)
    }

    fun addToFavourites(movieElementModel: MovieElementModel) {
        val movieId = movieElementModel.id

        repository.addMovieToFavourites(
            id = movieId,
            onFailureAction = {

            },
            onResponseAction = {
                favouriteMovieList.add(movieElementModel)
            }
        )
    }

    fun removeFromFavourites(movieElementModel: MovieElementModel) {
        val movieId = movieElementModel.id

        repository.removeMovieFromFavourites(
            id = movieId,
            onFailureAction = {

            },
            onResponseAction = {
                favouriteMovieList.remove(movieElementModel)
            }
        )
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