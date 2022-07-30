package com.github.yeeun_yun97.toy.mobymovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.github.yeeun_yun97.toy.mobymovie.data.model.MovieData
import com.github.yeeun_yun97.toy.mobymovie.data.model.MovieList
import com.github.yeeun_yun97.toy.mobymovie.data.model.NaverMovie
import com.github.yeeun_yun97.toy.mobymovie.data.model.NaverSearchResponse
import com.github.yeeun_yun97.toy.mobymovie.data.retrofit.NaverMovieService

class MovieRepository private constructor() {
    private val _service = NaverMovieService.getInstance()

    private var _searchedResponse: MovieList? = null
    private val _searchedRawList = MutableLiveData<MovieList>()
    val searchedList: LiveData<List<MovieData>>
        get() =
            Transformations.switchMap(_searchedRawList) {
                MutableLiveData(it.items)
            }

    companion object {
        private lateinit var _repo: MovieRepository
        fun getInstance(): MovieRepository {
            if (!this::_repo.isInitialized) {
                _repo = MovieRepository()
            }
            return _repo
        }
    }

    private fun convertToMovieList(origin: NaverSearchResponse, keyword: String): MovieList {
        return MovieList(
            start = origin.start,
            total = origin.total,
            keyword = keyword,
            items = convertToMovieDataList(origin.items)
        )
    }

    private fun convertToMovieDataList(origin: List<NaverMovie>): MutableList<MovieData> {
        val list = mutableListOf<MovieData>()
        for (movie in origin) {
            list.add(
                MovieData(
                    movieDate = movie.pubDate,
                    movieScore = movie.userRating,
                    movieThumbnail = movie.image,
                    movieTitle = movie.title,
                    movieUrl = movie.link
                )
            )
        }
        return list
    }


    suspend fun searchByKeywordAndPage(keyword: String, page: Int): Int {
        val response = _service.searchMovie(keyword, page)
        if (response.isSuccessful) {
            val data = convertToMovieList(response.body()!!, keyword)
            updateResponse(data)
        }
        return response.code()
    }

    private fun updateResponse(data: MovieList) {
        this._searchedResponse = data
        this._searchedRawList.postValue(_searchedResponse)
    }

    suspend fun loadNextOfPrevSearch(): Int {
        val prevData = _searchedResponse!!
        val keyword = prevData.keyword
        val start = prevData.start + 20

        if (start > prevData.total) return -1

        val response = _service.searchMovie(keyword, start)
        if (response.isSuccessful) {
            val data = convertToMovieDataList(response.body()!!.items)
            prevData.items.addAll(data)
            prevData.start = start
            updateResponse(prevData)
        }
        return response.code()
    }
}