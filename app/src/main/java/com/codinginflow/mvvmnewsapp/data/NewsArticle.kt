package com.codinginflow.mvvmnewsapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//data class 不需要{}否则需要构造器

@Entity(tableName = "News_articles")
data class NewsArticle (
    @PrimaryKey val url:String,
    val title :String?,
    val thumbnailUrl:String?,
    val isBookmarked: Boolean,
    val updateAt : Long = System.currentTimeMillis()
        )



@Entity(tableName = "breaking_news")
data class BreakingNews (
    val articleUrl:String,
    @PrimaryKey(autoGenerate = true) val id:Int = 0
)


