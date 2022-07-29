package com.github.yeeun_yun97.toy.mobymovie.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.github.yeeun_yun97.toy.mobymovie.data.model.History
import com.github.yeeun_yun97.toy.mobymovie.data.model.MovieData
import com.github.yeeun_yun97.toy.mobymovie.data.repository.HistoryRepository
import com.github.yeeun_yun97.toy.mobymovie.data.repository.MovieRepository

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val _movieRepo = MovieRepository()
    private val _historyRepo = HistoryRepository(application.applicationContext)

    val bindingKeyword = MutableLiveData("")
    val bindingSearchedList: LiveData<List<MovieData>> = _movieRepo.searchedList
    val historyList: LiveData<List<History>> = _historyRepo.historyList

    suspend fun saveKeywordToHistory() {
        val keyword = bindingKeyword.value
        if (!keyword.isNullOrEmpty())
            _historyRepo.insertHistory(keyword)
    }

    suspend fun searchStart() :Int{
        val keyword = bindingKeyword.value
        if (!keyword.isNullOrEmpty())
           return _movieRepo.searchByKeywordAndPage(keyword, 1)
        return -1
    }

    suspend fun loadMore() = _movieRepo.loadNextOfPrevSearch()


}