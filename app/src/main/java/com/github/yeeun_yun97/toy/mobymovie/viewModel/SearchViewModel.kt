package com.github.yeeun_yun97.toy.mobymovie.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.yeeun_yun97.toy.mobymovie.data.model.History
import com.github.yeeun_yun97.toy.mobymovie.data.model.MovieData
import com.github.yeeun_yun97.toy.mobymovie.data.repository.HistoryRepository
import com.github.yeeun_yun97.toy.mobymovie.data.repository.MovieRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _movieRepo = MovieRepository()
    private val _historyRepo = HistoryRepository()

    val bindingKeyword = MutableLiveData("")
    val bindingSearchedList: LiveData<List<MovieData>> = _movieRepo.searchedList
    val historyList: LiveData<List<History>> = _historyRepo.historyList

    fun searchStart() =
        viewModelScope.launch {
            val keyword = bindingKeyword.value ?: ""
            _movieRepo.searchByKeywordAndPage(keyword, 1)
        }
}