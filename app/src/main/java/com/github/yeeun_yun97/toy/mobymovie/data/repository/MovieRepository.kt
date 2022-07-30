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
    private val service = NaverMovieService.getInstance()

    private var _movieList: MovieList? = null
    private val _movieListMutableLiveData = MutableLiveData<MovieList>()
    val movieList: LiveData<List<MovieData>>
        get() = Transformations.switchMap(_movieListMutableLiveData) {
            MutableLiveData(it.items)
        }

    companion object {
        private lateinit var repo: MovieRepository
        fun getInstance(): MovieRepository {
            if (!this::repo.isInitialized)
                repo = MovieRepository()
            return repo
        }
    }

    /**
     * 새로운 검색어로 첫 데이터를 불러온다.
     * @return 성공:200, 실패:그 외(401.. 등).
     */
    suspend fun searchByKeywordAndPage(keyword: String, page: Int): Int {
        val response = service.searchMovie(keyword, page)
        if (response.isSuccessful) {
            val data = convertToMovieList(response.body()!!, keyword)
            updateMovieList(data)
        }
        return response.code()
    }

    /**
     * 지난번에 검색한 검색어로 다음 데이터를 불러온다.
     * @return 불러올 값 없음:-1, 성공:200, 실패:그 외(401.. 등).
     */
    suspend fun loadNextOfPrevSearch(): Int {
        val prevData = _movieList!!
        val keyword = prevData.keyword
        val start = prevData.start + 20

        if (start > prevData.total) return -1

        val response = service.searchMovie(keyword, start)
        if (response.isSuccessful) {
            val data = convertToMovieDataList(response.body()!!.items)
            prevData.items.addAll(data)
            prevData.start = start
            updateMovieList(prevData)
        }
        return response.code()
    }

    private fun updateMovieList(data: MovieList) {
        this._movieList = data
        this._movieListMutableLiveData.postValue(_movieList)
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


}
