package com.github.yeeun_yun97.toy.mobymovie.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.github.yeeun_yun97.toy.mobymovie.data.model.MovieData
import com.github.yeeun_yun97.toy.mobymovie.data.model.NaverMovie
import com.github.yeeun_yun97.toy.mobymovie.data.model.NaverSearchResponse
import com.github.yeeun_yun97.toy.mobymovie.data.retrofit.NaverMovieService

class MovieRepository {
    private val _service = NaverMovieService.getInstance()

    private val _searchedRawList: MutableLiveData<NaverSearchResponse> = MutableLiveData()
    val searchedList: LiveData<List<MovieData>>
        get() =
            Transformations.switchMap(_searchedRawList) {
                val list = mutableListOf<MovieData>()
                for (movie in it.items) {
                    list.add(convertToMovieData(movie))
                }
                MutableLiveData(list)
            }

    private fun convertToMovieData(origin: NaverMovie): MovieData {
        return MovieData(
            movieDate = origin.pubDate,
            movieScore = origin.userRating,
            movieThumbnail = origin.image,
            movieTitle = origin.title,
            movieUrl = origin.link
        )
    }


    suspend fun searchByKeywordAndPage(keyword: String, page: Int): Int {
        val response = _service.searchMovie(keyword,
            page
        )
        if (response.isSuccessful) {
//            YnConfirmBaseDialogFragment(
//                "실패 (${response.code()})",
//                "검색 결과를 받아오는 데 실패하였습니다.",
//                null
//            )
            this._searchedRawList.postValue(response.body())
            Log.d("불러옴", response.body().toString())
        }
        else Log.d("에러", "${response.code()}, ${response.errorBody()?.string()}")
        return response.code()
    }
}