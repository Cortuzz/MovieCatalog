package com.example.mobiledevelopment.src.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.mobiledevelopment.include.retrofit.MovieElementModel
import com.example.mobiledevelopment.include.retrofit.MoviesListModel
import com.example.mobiledevelopment.include.retrofit.MoviesPageListModel
import com.example.mobiledevelopment.src.MainActivity
import java.util.Collections.shuffle

class MainViewModel(private val view: MainView, private val activity: MainActivity) {
    private val repository: MainRepository = MainRepository()
    private var promotedMovie: MutableState<MovieElementModel?> = mutableStateOf(null)
    private var movieList = mutableStateListOf<MovieElementModel>()
    private var favouriteMovieList = mutableStateListOf<MovieElementModel>()

    init {
        repository.getMovies(1,
            onResponseAction = {
                parseMoviesModel(it.body() as MoviesPageListModel, true)
            },
            onFailureAction = {
                // todo: throw Exception()
            }
        )

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

    private fun parseMoviesModel(moviesPageModel: MoviesPageListModel, addToPromoted: Boolean = false) {
        val movies = (moviesPageModel.movies).shuffled().toMutableList()

        if (addToPromoted) {
            promotedMovie.value = movies.removeFirst()
        }

        for (movie in movies) {
            movieList.add(movie)
        }
    }

    fun fetchNextPage() {

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