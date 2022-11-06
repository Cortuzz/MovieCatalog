package com.example.mobiledevelopment.src.domain.retrofit

import com.example.mobiledevelopment.src.domain.models.*
import retrofit2.Call
import retrofit2.http.*



interface RetrofitServices {
    @GET("movies/details/{id}/")
    fun getMovie(
        @Path("id") id: String
    ): Call<MovieDetailsModel>

    @GET("movies/{page}/")
    fun getMoviesList(
        @Path("page") page: Int
    ): Call<MoviesPageListModel>

    @POST("account/login/")
    fun loginUser(
        @Body loginModel: UserLoginModel
    ): Call<UserTokenModel>

    @POST("account/register/")
    fun registerUser(
        @Body registerModel: UserRegisterModel
    ): Call<UserTokenModel>

    @GET("account/profile/")
    fun getProfile(
        @Header("Authorization") authToken: String
    ): Call<ProfileModel>

    @GET("favorites/")
    fun getFavouritesMovies(
        @Header("Authorization") authToken: String
    ): Call<MoviesListModel>

    @POST("favorites/{id}/add/")
    fun addToFavourites(
        @Header("Authorization") authToken: String,
        @Path("id") id: String
    ): Call<Void>

    @DELETE("favorites/{id}/delete/")
    fun removeFromFavourites(
        @Header("Authorization") authToken: String,
        @Path("id") id: String
    ): Call<Void>

    @POST("movie/{id}/review/add/")
    fun addReview(
        @Header("Authorization") authToken: String,
        @Path("id") id: String,
        @Body reviewModel: ReviewModifyModel
    ): Call<Void>

    @DELETE("movie/{movieId}/review/{reviewId}/delete/")
    fun deleteReview(
        @Header("Authorization") authToken: String,
        @Path("movieId") movieId: String,
        @Path("reviewId") reviewId: String
    ): Call<Void>

    @PUT("account/profile/")
    fun updateProfile(
        @Header("Authorization") authToken: String,
        @Body profileModel: ProfileModel
    ): Call<Void>

    @POST("account/logout/")
    fun logout(
        @Header("Authorization") authToken: String
    ): Call<Void>
}