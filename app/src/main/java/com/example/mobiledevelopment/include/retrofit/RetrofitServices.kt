package com.example.mobiledevelopment.include.retrofit

import androidx.compose.ui.graphics.Color
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

data class GenreModel(
    var id: String,
    var name: String?
)

data class UserShortModel(
    var userId: String,
    var nickName: String?,
    var avatar: String?
)

data class ReviewModel(
    var id: String,
    var rating: Int,
    var reviewText: String?,
    var isAnonymous: Boolean,
    var createDateTime: String,
    var author: UserShortModel
)

data class ReviewShortModel(
    var id: String,
    var rating: Int
)

data class MovieDetailsModel(
    var id: String,
    var name: String?,
    var poster: String?,
    var year: Int,
    var country: String?,
    var genres: List<GenreModel>,
    var reviews: List<ReviewModel>,
    var time: Int,
    var tagline: String?,
    var director: String?,
    var budget: Int?,
    var fees: Int?,
    var ageLimit: Int
)

data class UserRegisterModel(
    var userName: String,
    var name: String,
    var password: String,
    var email: String,
    var birthDate: String?,
    var gender: Int?
)

data class ProfileModel(
    var id: String,
    var nickName: String?,
    var email: String,
    var avatarLink: String?,
    var name: String,
    var birthDate: String?,
    var gender: Int?
)

data class UserLoginModel(
    var username: String,
    var password: String
)

data class UserTokenModel(
    var token: String
)

data class MoviesPageListModel(
    var movies: List<MovieElementModel>,
    var pageInfo: PageInfoModel
)

data class MoviesListModel(
    var movies: List<MovieElementModel>
)

data class PageInfoModel(
    var pageSize: Int,
    var pageCount: Int,
    var currentPage: Int
)

data class MovieElementModel(
    var id: String,
    var name: String?,
    var poster: String?,
    var year: String,
    var country: String?,
    var genres: List<GenreModel>?,
    var reviews: List<ReviewShortModel>?
)

interface RetrofitServices {
    @GET("movies/details/{id}/")
    fun getMovie(@Path("id") id: String): Call<MovieDetailsModel>

    @GET("movies/{page}/")
    fun getMoviesList(@Path("page") page: Int): Call<MoviesPageListModel>

    @POST("account/login/")
    fun loginUser(@Body loginModel: UserLoginModel): Call<UserTokenModel>

    @POST("account/register/")
    fun registerUser(@Body registerModel: UserRegisterModel): Call<UserTokenModel>

    @GET("account/profile/")
    fun getProfile(@Header("Authorization") authToken: String): Call<ProfileModel>

    @GET("favorites/")
    fun getFavouritesMovies(@Header("Authorization") authToken: String): Call<MoviesListModel>

    @POST("favorites/{id}/add/")
    fun addToFavourites(@Header("Authorization") authToken: String, @Path("id") id: String): Call<JsonElement>
}