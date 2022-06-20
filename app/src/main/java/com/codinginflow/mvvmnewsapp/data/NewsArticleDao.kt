package com.codinginflow.mvvmnewsapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsArticleDao {


    @Query("SELECT * FROM breaking_news INNER JOIN News_articles ON articleUrl=url")
    fun getAllBreakingNewsArticles(): Flow<List<NewsArticle>>

    //冲突策略是取代旧数据同时继续事务
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<NewsArticle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreakingArticles(breakingNews: List<BreakingNews>)

    @Query("DELETE FROM breaking_news")
    suspend fun deleteAllBreakingNews()


}