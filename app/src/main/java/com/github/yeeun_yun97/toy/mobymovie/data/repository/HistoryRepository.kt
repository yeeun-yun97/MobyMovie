package com.github.yeeun_yun97.toy.mobymovie.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.github.yeeun_yun97.toy.mobymovie.data.model.History
import com.github.yeeun_yun97.toy.mobymovie.data.room.HistoryDao
import com.github.yeeun_yun97.toy.mobymovie.data.room.HistoryDatabase

class HistoryRepository(applicationContext: Context) {
    private val _dao: HistoryDao = HistoryDatabase.getInstance(applicationContext).getDao()
    val historyList: LiveData<List<History>> get() = _dao.getHistories()

    suspend fun insertHistory(keyword: String) {
        _dao.deleteDuplicates(keyword)
        _dao.insertHistory(History(keyword = keyword))

        val count = _dao.countHistory()
        if (count > 10) {
            _dao.deleteOldsByLimit(count - 10)
        }
    }
}