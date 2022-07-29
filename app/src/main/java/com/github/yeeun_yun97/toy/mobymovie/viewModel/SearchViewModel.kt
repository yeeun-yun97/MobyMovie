package com.github.yeeun_yun97.toy.mobymovie.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.yeeun_yun97.toy.mobymovie.data.model.MovieData
import com.github.yeeun_yun97.toy.mobymovie.data.repository.MovieRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _movieRepo = MovieRepository()
    private val _bindingSearchedList = _movieRepo.searchedList

    val bindingKeyword = MutableLiveData("")
    val bindingSearchedList: LiveData<List<MovieData>> = _bindingSearchedList

    fun searchStart() =
        viewModelScope.launch {
            val keyword = bindingKeyword.value ?: ""
            _movieRepo.searchByKeywordAndPage(keyword, 1)
        }
}