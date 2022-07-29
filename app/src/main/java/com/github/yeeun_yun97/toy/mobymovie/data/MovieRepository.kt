package com.github.yeeun_yun97.toy.mobymovie.data

import androidx.lifecycle.MutableLiveData

class MovieRepository {
    val searchedList = MutableLiveData<List<MovieData>>(listOf())

    fun searchByKeyword(keyword:String){
        val list = listOf<MovieData>(
            MovieData(
                "미니언즈",
                "10.0",
                "https://movie-phinf.pstatic.net/20220720_283/1658284839003UzxoT_JPEG/movie_image.jpg?type=m203_290_2",
                "2022.02.02",
            )
        )
        searchedList.postValue(list)
    }
}