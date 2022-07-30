package com.github.yeeun_yun97.toy.mobymovie.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.github.yeeun_yun97.toy.mobymovie.data.model.History
import com.github.yeeun_yun97.toy.mobymovie.data.model.MovieData
import com.github.yeeun_yun97.toy.mobymovie.data.repository.HistoryRepository
import com.github.yeeun_yun97.toy.mobymovie.data.repository.MovieRepository

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val _movieRepo = MovieRepository.getInstance()
    private val _historyRepo = HistoryRepository.getInstance(application.applicationContext)

    val bindingKeyword = MutableLiveData("")
    val movieList: LiveData<List<MovieData>> = _movieRepo.movieList
    val historyList: LiveData<List<History>> = _historyRepo.historyList

    /**
     * viewModel의 keyword를 history 테이블에 저장
     */
    suspend fun saveKeywordToHistory() {
        val keyword = bindingKeyword.value
        if (!keyword.isNullOrEmpty())
            _historyRepo.insertHistory(keyword)
    }

    /**
     * viewModel의 keyword로 최초 검색 시작
     * @return keyword가 null임:-1, 성공:200, 실패:그 외(401,..)
     */
    suspend fun searchStart(): Int {
        val keyword = bindingKeyword.value
        if (keyword.isNullOrEmpty()) return -1
        return _movieRepo.searchByKeywordAndPage(keyword, 1)
    }

    /**
     * 지난번에 검색한 keyword로 다음 데이터를 불러온다.
     * @return 불러올 값 없음:-1, 성공:200, 실패:그 외(401.. 등).
     */
    suspend fun loadMore() = _movieRepo.loadNextOfPrevSearch()


}