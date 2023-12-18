package com.test.arabamcom.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://sandbox.arabamd.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
}