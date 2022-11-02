package com.example.mobiledevelopment.include.retrofit

object Common {
    private const val COMMON_API = true

    private const val COMMON_URL = "https://react-midterm.kreosoft.space/api/"
    private const val SERVER_URL = "https://26.4.93.126:7268/api/"
    var userToken: String = ""
    var currentMovieId: String = ""
    var userId: String = ""

    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(if (COMMON_API) COMMON_URL else SERVER_URL).create(RetrofitServices::class.java)
}
