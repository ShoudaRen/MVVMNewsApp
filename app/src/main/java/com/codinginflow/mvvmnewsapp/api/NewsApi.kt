package com.codinginflow.mvvmnewsapp.api

import com.codinginflow.mvvmnewsapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface NewsApi {

    companion object{
        const val BASE_URL="https://newsapi.org/v2/"
        const val API_KEY=BuildConfig.NEWS_API_ACCESS_KEY
    }

   //attach your API key to a request
    @Headers("X-Api-Key : $API_KEY")
    @GET("top-headlines?country=us&pageSize=100")
    // when we use a suspend function to perform a request, the underlying thread isn't blocked.
    suspend fun getBreakingNews():NewsResponse

    @Headers("X-Api-Key : $API_KEY")
    @GET("everything")
    suspend fun SearchNews(
        @Query("q") query: String,
        @Query("page") page:Int,
        @Query("pageSize") pageSize: Int
    ): NewsResponse
}