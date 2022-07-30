package com.github.yeeun_yun97.toy.mobymovie.data.model

data class MovieList(
    var start:Int,
    val total:Int,
    val keyword:String,
    val items:MutableList<MovieData>
)

data class MovieData(
    val movieTitle: String,
    val movieScore: String,
    val movieThumbnail: String,
    val movieDate: String,
    val movieUrl: String
)
