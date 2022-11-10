package com.example.mobiledevelopment.src.domain.models

data class GenreModel(
    var id: String,
    var name: String?,
    var isActive: Boolean = false
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
    var author: UserShortModel?,
    var isMine: Boolean?
)

data class ReviewShortModel(
    var id: String,
    var rating: Int
)

data class MovieDetailsModel(
    var id: String,
    var description: String?,
    var name: String?,
    var poster: String?,
    var year: Int,
    var country: String?,
    var genres: List<GenreModel>,
    var reviews: MutableList<ReviewModel>,
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
    var gender: Int?,
    var role: String?
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

data class ReviewModifyModel(
    var reviewText: String,
    var rating: Int,
    var isAnonymous: Boolean,
)

data class GenresModel(
    var genres: List<GenreModel>
)

data class InsertMovieModel(
    var name: String,
    var poster: String,
    var year: Int,
    var country: String,
    var time: Int,
    var tagline: String,
    var description: String,
    var director: String,
    var budget: Int,
    var fees: Int,
    var ageLimit: Int,
    var genres: List<GenreModel>
)