package com.test.arabamcom.api

import retrofit2.Response
import retrofit2.http.GET

interface SimpleApi {
    @GET("listing?sort=1&sortDirection=0&take=420")
    suspend fun fetchPost(): Response<List<PostModel>>

}


data class PostModel(
    val id: Long,
    val title: String,
    val location: Location,
    val category: Category,
    val modelName: String,
    val price: Long,
    val priceFormatted: String,
    val date: String,
    val dateFormatted: String,
    val photos: List<String>,
    val properties: List<Property>,
    val text: String,
    val userInfo: UserInfo
): java.io.Serializable


data class Location(
    val cityName: String,
    val townName: String
)

data class Category(
    val id: Long,
    val name: String
)

data class Property(
    val name: String,
    val value: String
)

data class UserInfo(
    val id: Long,
    val nameSurname: String,
    val phone: String,
    val phoneFormatted: String
)