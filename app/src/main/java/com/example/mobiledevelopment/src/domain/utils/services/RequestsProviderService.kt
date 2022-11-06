package com.example.mobiledevelopment.src.domain.utils.services

import android.util.Log
import com.example.mobiledevelopment.src.domain.models.*
import com.example.mobiledevelopment.src.domain.retrofit.Common
import com.example.mobiledevelopment.src.domain.utils.SharedStorage
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestsProviderService {
    private val service = Common.retrofitService

    fun registerUser(
        registerModel: UserRegisterModel,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int, ResponseBody) -> Unit,
        onResponseAction: (Int, UserTokenModel) -> Unit,
    ) {
        service.registerUser(registerModel).enqueue(object :
            Callback<UserTokenModel> {
            override fun onFailure(call: Call<UserTokenModel>, t: Throwable) {
                onFailureAction()
                Log.e("Network manager", "Registration failed with exception: $t")
            }

            override fun onResponse(call: Call<UserTokenModel>, response: Response<UserTokenModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    onBadResponseAction(response.code(), response.errorBody() as ResponseBody)
                    Log.w("Network manager", "Registration failed with response: ${response.errorBody()?.charStream()?.readText()}")
                    return
                }

                onResponseAction(response.code(), response.body() as UserTokenModel)
                Log.i("Network manager", "Registration successful. Response: ${response.body()}")
            }
        })
    }

    fun loginUser(
        loginModel: UserLoginModel,
        onResponseAction: (Int, UserTokenModel) -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int, ResponseBody) -> Unit,
    ) {
        service.loginUser(loginModel).enqueue(object : Callback<UserTokenModel> {
            override fun onFailure(call: Call<UserTokenModel>, t: Throwable) {
                onFailureAction()
                Log.e("Network manager", "Login failed with exception: $t")
            }

            override fun onResponse(call: Call<UserTokenModel>, response: Response<UserTokenModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    onBadResponseAction(response.code(), response.errorBody() as ResponseBody)
                    Log.w("Network manager", "Login failed with response: ${response.errorBody()?.charStream()?.readText()}")
                    return
                }
                Log.i("Network manager", "Login successful. Response: ${response.body()}")
                onResponseAction(response.code(), response.body() as UserTokenModel)
            }
        })
    }

    fun getProfile(
        onResponseAction: (Int, ProfileModel) -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int, ResponseBody) -> Unit
    ) {
        service.getProfile("Bearer ${SharedStorage.userToken}").enqueue(object :
            Callback<ProfileModel> {
            override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                onFailureAction()
            }

            override fun onResponse(call: Call<ProfileModel>, response: Response<ProfileModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    onBadResponseAction(response.code(), response.errorBody() as ResponseBody)
                    return
                }

                onResponseAction(response.code(), response.body() as ProfileModel)
            }
        })
    }

    fun updateProfile(
        onResponseAction: (Int) -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int, ResponseBody) -> Unit,
        profileModel: ProfileModel
    ) {
        service.updateProfile("Bearer ${SharedStorage.userToken}", profileModel).enqueue(object :
            Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                onFailureAction()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    onBadResponseAction(response.code(), response.errorBody() as ResponseBody)
                    return
                }

                onResponseAction(response.code())
            }
        })
    }

    fun logout(
        onResponseAction: (Int) -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int, ResponseBody) -> Unit,
    ) {
        service.logout("Bearer ${SharedStorage.userToken}").enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                onFailureAction()

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    onBadResponseAction(response.code(), response.errorBody() as ResponseBody)
                    return
                }

                onResponseAction(response.code())
            }
        })
    }

    fun getMovie(
        onResponseAction: (Int, MovieDetailsModel) -> Unit,
        onBadResponseAction: (Int, ResponseBody) -> Unit,
        onFailureAction: () -> Unit,
    ) {
        service.getMovie(SharedStorage.currentMovieId).enqueue(object : Callback<MovieDetailsModel> {
            override fun onFailure(call: Call<MovieDetailsModel>, t: Throwable) {
                Log.e("Network manager", "Fetching movie failed with exception: $t")
                onFailureAction()
            }

            override fun onResponse(call: Call<MovieDetailsModel>, response: Response<MovieDetailsModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    Log.w("Network manager", "Fetching movies failed with response: ${response.errorBody()?.charStream()?.readText()}")
                    onBadResponseAction(response.code(), response.errorBody() as ResponseBody)
                    return
                }

                Log.i("Network manager", "Movie successfully fetched. Response: ${response.body()}")
                onResponseAction(response.code(), response.body() as MovieDetailsModel)
            }
        })
    }

    fun getMovies(
        page: Int,
        onResponseAction: (Int, MoviesPageListModel) -> Unit,
        onBadResponseAction: (Int, ResponseBody) -> Unit,
        onFailureAction: () -> Unit,
    ) {
        service.getMoviesList(page).enqueue(object : Callback<MoviesPageListModel> {
            override fun onFailure(call: Call<MoviesPageListModel>, t: Throwable) {
                Log.e("Network manager", "Fetching movies failed with exception: $t")
                onFailureAction()
            }

            override fun onResponse(call: Call<MoviesPageListModel>, response: Response<MoviesPageListModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    Log.w("Network manager", "Fetching movies failed with response: ${response.errorBody()?.charStream()?.readText()}")
                    onBadResponseAction(response.code(), response.errorBody() as ResponseBody)
                    return
                }

                Log.i("Network manager", "Movies fetched successfully. Response: ${response.body()}")
                onResponseAction(response.code(), response.body() as MoviesPageListModel)
            }
        })
    }

    fun getFavouriteMovies(
        onResponseAction: (Int, MoviesListModel) -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int, ResponseBody) -> Unit
    ) {
        service.getFavouritesMovies("Bearer ${SharedStorage.userToken}").enqueue(object : Callback<MoviesListModel> {
            override fun onFailure(call: Call<MoviesListModel>, t: Throwable) {
                onFailureAction()
                Log.e("Network manager", "Fetching favourites movies failed with exception: $t")
            }

            override fun onResponse(call: Call<MoviesListModel>, response: Response<MoviesListModel>) {
                if (!response.isSuccessful || response.body() == null) {
                    Log.w("Network manager", "Fetching favourites movies failed with code ${response.code()} and response: ${response.errorBody()?.charStream()?.readText()}")
                    onBadResponseAction(response.code(), response.errorBody() as ResponseBody)
                    return
                }

                Log.i("Network manager", "Favourites movies fetched successfully. Code: ${response.code()} Response: ${response.body()}")
                onResponseAction(response.code(), response.body() as MoviesListModel)
            }
        })
    }

    fun addMovieToFavourites(
        id: String,
        onResponseAction: (Int) -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int, ResponseBody) -> Unit
    ) {
        service.addToFavourites("Bearer ${SharedStorage.userToken}", id).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                onFailureAction()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    onBadResponseAction(response.code(), response.errorBody() as ResponseBody)
                    return
                }

                onResponseAction(response.code())
            }
        })
    }

    fun removeMovieFromFavourites(
        id: String,
        onResponseAction: (Int) -> Unit,
        onBadResponseAction: (Int, ResponseBody) -> Unit,
        onFailureAction: () -> Unit,
    ) {
        service.removeFromFavourites("Bearer ${SharedStorage.userToken}", id).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Network manager", "Removing movie from favourites failed with exception: $t")
                onFailureAction()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    Log.w("Network manager", "Removing movie from favourites failed with code ${response.code()} and response: ${response.errorBody()?.charStream()?.readText()}")
                    onBadResponseAction(response.code(), response.errorBody() as ResponseBody)
                    return
                }

                Log.i("Network manager", "Movie removed from favourites successfully. Code: ${response.code()} Response: ${response.body()}")
                onResponseAction(response.code())
            }
        })
    }

    fun sendReview(
        id: String,
        reviewModifyModel: ReviewModifyModel,
        onResponseAction: (Int) -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int, ResponseBody) -> Unit
    ) {
        service.addReview( "Bearer ${SharedStorage.userToken}",id, reviewModifyModel).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Network manager", "Sending review failed with exception: $t")
                onFailureAction()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    Log.w("Network manager", "Sending review failed with response: ${response.errorBody()?.charStream()?.readText()}")
                    onBadResponseAction(response.code(), response.errorBody() as ResponseBody)
                    return
                }

                Log.i("Network manager", "Review successfully sent. Response: ${response.body()}")
                onResponseAction(response.code())
            }
        })
    }

    fun deleteReview(
        id: String,
        onResponseAction: (Int) -> Unit,
        onFailureAction: () -> Unit,
        onBadResponseAction: (Int, ResponseBody) -> Unit
    ) {
        service.deleteReview( "Bearer ${SharedStorage.userToken}", SharedStorage.currentMovieId, id).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Network manager", "Deleting review failed with exception: $t")
                onFailureAction()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    Log.w("Network manager", "Deleting review failed with response: ${response.errorBody()?.charStream()?.readText()}")
                    onBadResponseAction(response.code(), response.errorBody() as ResponseBody)
                    return
                }

                Log.i("Network manager", "Review successfully deleted. Response: ${response.body()}")
                onResponseAction(response.code())
            }
        })
    }
}