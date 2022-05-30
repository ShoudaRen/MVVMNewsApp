package com.codinginflow.mvvmnewsapp.api

import com.codinginflow.mvvmnewsapp.BuildConfig


interface NewsApi {

    companion object{
        const val BASE_URL="https://newsapi.org/v2/"
        const val API_KEY=BuildConfig.NEWS_API_ACCESS_KEY
    }


    suspend fun getBreakingNews():NewsResponse

    suspend fun SearchNews(

    ): NewsResponse
}