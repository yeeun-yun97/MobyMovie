package com.github.yeeun_yun97.toy.mobymovie.data.model

data class NaverSearchResponse (
    val total:Int,
    val start:Int,
    val items:List<NaverMovie>
)
data class NaverMovie(
    val title:String,
    val link:String,
    val image:String = "",
    val pubDate:String,
    val userRating:String
)

