package com.codinginflow.mvvmnewsapp.di

import com.codinginflow.mvvmnewsapp.api.NewsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//单例模式
@Module //该类或者接口用于提供相关依赖
object AppModule {

    @Provides      //注解在非抽象方法上，返回值是程序中需要用到的依赖。
    @Singleton   //注解可以保证被注解的对象全局都是单例
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())//用Gson来进行反序列化的
            .build()








}