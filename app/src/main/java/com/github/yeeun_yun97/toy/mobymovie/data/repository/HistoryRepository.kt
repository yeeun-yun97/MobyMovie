package com.github.yeeun_yun97.toy.mobymovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.yeeun_yun97.toy.mobymovie.data.model.History

class HistoryRepository {
    private val _historyList = MutableLiveData(listOf<History>(
        History(1,"테스트1"),
        History(2,"테스트2"),
        History(3,"테스트3"),
        History(4,"테스트4"),
    ))
    val historyList : LiveData<List<History>> get() = _historyList




}