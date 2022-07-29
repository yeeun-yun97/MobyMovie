package com.github.yeeun_yun97.toy.mobymovie.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.yeeun_yun97.toy.mobymovie.data.MovieData
import com.github.yeeun_yun97.toy.mobymovie.data.MovieRepository

class SearchViewModel :ViewModel(){
    private val _movieRepo = MovieRepository()
    private val _bindingSearchedList = _movieRepo.searchedList

    val bindingKeyword = MutableLiveData("")
    val bindingSearchedList : LiveData<List<MovieData>> = _bindingSearchedList

    fun searchStart(){
        val keyword = bindingKeyword.value ?: ""
        _movieRepo.searchByKeyword(keyword)
    }
}