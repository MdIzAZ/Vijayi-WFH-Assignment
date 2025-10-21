package com.example.vijayiwfhassignment.data.api

import com.example.vijayiwfhassignment.data.models.TitleDetailsResponse
import com.example.vijayiwfhassignment.data.models.TitlesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WatchmodeApiService {

    companion object {
        const val BASE_URL = "https://api.watchmode.com/v1/"
//        const val API_KEY = "jpwz6vdjqztNCgrS5pVwYlPwH9bniT7G8iItJNmd"
        const val API_KEY = "6TtU97VnOuYxX8TyxNKtnFWUnDU9tBWXsABsM40I"
    }

    @GET("list-titles")
    fun getTitles(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("source_ids") sourceIds: String = "203,26",
        @Query("types") types: String,
        @Query("limit") limit: Int = 20
    ): Single<Response<TitlesResponse>>

    @GET("title/{title_id}/details/")
    fun getTitleDetails(
        @Path("title_id") titleId: Int,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("append_to_response") appendToResponse: String = "sources"
    ): Single<Response<TitleDetailsResponse>>
}

