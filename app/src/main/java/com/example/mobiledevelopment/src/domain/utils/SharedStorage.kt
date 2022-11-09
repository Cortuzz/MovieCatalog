package com.example.mobiledevelopment.src.domain.utils

object SharedStorage {
    var userToken: String = ""
    var currentMovieId: String = ""
    var userId: String = ""
    var isTokenValid = false
    var isRefreshNeeded = false
}