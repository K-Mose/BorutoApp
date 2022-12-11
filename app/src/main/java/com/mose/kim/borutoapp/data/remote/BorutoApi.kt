package com.mose.kim.borutoapp.data.remote

import com.mose.kim.borutoapp.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit RESTFull API. Ktor server와 endpoint 같게
interface BorutoApi {

    @GET("/all/heroes")
    suspend fun getAllHeroes(
        @Query("page") page: Int = 1 // queryParameter
    ): ApiResponse

    @GET("/heroes/search")
    suspend fun searchHeroes() {

    }
}