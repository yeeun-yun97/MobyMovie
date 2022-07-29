package com.github.yeeun_yun97.toy.mobymovie.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.github.yeeun_yun97.toy.mobymovie.data.model.History
import com.github.yeeun_yun97.toy.mobymovie.data.model.MovieData
import com.github.yeeun_yun97.toy.mobymovie.data.repository.HistoryRepository
import com.github.yeeun_yun97.toy.mobymovie.data.repository.MovieRepository
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val _movieRepo = MovieRepository()
    private val _historyRepo = HistoryRepository(application.applicationContext)

    val bindingKeyword = MutableLiveData("")
    val bindingSearchedList: LiveData<List<MovieData>> = _movieRepo.searchedList
    val historyList: LiveData<List<History>> = _historyRepo.historyList

    fun saveKeywordToHistory() =
        viewModelScope.launch {
            val keyword = bindingKeyword.value ?: ""
            _historyRepo.insertHistory(keyword)
        }

    fun searchStart() =
        viewModelScope.launch {
            val keyword = bindingKeyword.value ?: ""
            _movieRepo.searchByKeywordAndPage(keyword, 1)
        }


}