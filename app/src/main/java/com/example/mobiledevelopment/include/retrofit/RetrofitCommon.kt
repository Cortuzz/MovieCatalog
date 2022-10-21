package com.example.mobiledevelopment.include.retrofit

object Common {
    private const val BASE_URL = "https://react-midterm.kreosoft.space/api/"
    var userToken: String = ""

    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}