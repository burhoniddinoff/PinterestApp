package com.example.pinterestapp.networking

import com.example.pinterestapp.model.ResponseItem
import com.example.pinterestapp.model.User
import com.example.pinterestapp.modelSearch.Welcome
import com.example.pinterestapp.modelSearch.Result
import com.example.pinterestapp.modelSearchFrag.CollectionsModelItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {

    @GET("photos/random?count=1000")
    fun listPhotos(): Call<ArrayList<ResponseItem>>

    @GET("search/photos?page=1&per_page=19&query=")
    fun searchPhotos(@Query("page") page: Int, @Query("query") search: String): Call<Welcome>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Call<User>

    @GET("PHOTOS/{id}")
    fun getImagesCategories(@Path("id") id: String): Call<Result>

    @GET("collections/")
    fun getCollections(): Call<CollectionsModelItem>

//    @GET("photos/{id}")
//    fun singlePhotos(@Path("id") id: Int): Call<ResponseItem>

//    @POST("photos")
//    fun createPhotos(@Body post: ResponseItem): Call<ResponseItem>

//    @PUT("photos/{id}")
//    fun updatePhotos(@Path("id") id: Int, @Body post: ResponseItem): Call<ResponseItem>

//    @DELETE("photos/{id}")
//    fun deletePhotos(@Path("id") id: Int): Call<ResponseItem>

//    @GET("search/photos?")
//    @GET("search/photos?page=2&query=tourism")

//    fun searchPhotos(@Query("query") search: String): Call<Welcome>
//    fun searchPhotos() : Call<Welcome>

}