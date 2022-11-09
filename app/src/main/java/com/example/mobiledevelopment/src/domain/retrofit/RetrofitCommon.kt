package com.example.mobiledevelopment.src.domain.retrofit

object Common {
    private const val COMMON_API = false

    private const val COMMON_URL = "https://react-midterm.kreosoft.space/api/"
    private const val SERVER_URL = "https:/90.188.106.99:7268/api/"

    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(if (COMMON_API) COMMON_URL else SERVER_URL)
            .create(RetrofitServices::class.java)
}
