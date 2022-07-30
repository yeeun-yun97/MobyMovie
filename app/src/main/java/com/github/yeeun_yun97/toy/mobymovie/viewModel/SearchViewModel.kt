package com.github.yeeun_yun97.toy.mobymovie.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.github.yeeun_yun97.toy.mobymovie.data.model.History
import com.github.yeeun_yun97.toy.mobymovie.data.model.MovieData
import com.github.yeeun_yun97.toy.mobymovie.data.repository.HistoryRepository
import com.github.yeeun_yun97.toy.mobymovie.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val movieRepo = MovieRepository.getInstance()
    private val historyRepo = HistoryRepository.getInstance(application.applicationContext)

    val bindingKeyword = MutableLiveData("")
    val movieList: LiveData<List<MovieData>> = movieRepo.movieList
    val historyList: LiveData<List<History>> = historyRepo.historyList

    /**
     * viewModel의 keyword를 history 테이블에 저장
     */
    fun saveKeywordToHistory() =
        viewModelScope.launch(Dispatchers.IO) {
            val keyword = bindingKeyword.value
            if (!keyword.isNullOrEmpty())
                historyRepo.insertHistory(keyword)
        }

    /**
     * viewModel의 keyword로 최초 검색 시작
     */
    fun searchStart(onError: (Int) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            val keyword = bindingKeyword.value
            if (!keyword.isNullOrEmpty()) {
                val result = movieRepo.searchByKeywordAndPage(keyword, 1)
                launch(Dispatchers.Main) {
                    if (result != 200 && result != -1) onError(result)
                }.join()
            }
        }

    /**
     * 지난번에 검색한 keyword로 다음 데이터를 불러온다.
     */
    fun loadMore(onError: (Int) -> Unit, onFinish: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            val result = movieRepo.loadNextOfPrevSearch()
            launch(Dispatchers.Main) {
                if (result != 200 && result != -1) onError(result)
                onFinish()
            }.join()
        }


}

