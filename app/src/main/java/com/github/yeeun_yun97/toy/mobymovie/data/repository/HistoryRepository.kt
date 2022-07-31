package com.github.yeeun_yun97.toy.mobymovie.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.github.yeeun_yun97.toy.mobymovie.data.model.History
import com.github.yeeun_yun97.toy.mobymovie.data.room.HistoryDao
import com.github.yeeun_yun97.toy.mobymovie.data.room.HistoryDatabase

class HistoryRepository private constructor(applicationContext: Context) {
    private val dao: HistoryDao = HistoryDatabase.getInstance(applicationContext).getDao()
    val historyList: LiveData<List<History>> get() = dao.getHistories()

    companion object {
        private lateinit var repo: HistoryRepository
        fun getInstance(applicationContext: Context): HistoryRepository {
            if (!this::repo.isInitialized) {
                repo = HistoryRepository(applicationContext)
            }
            return repo
        }
    }

    /**
     * 키워드를 History Table에 저장함.
     * @param keyword 저장할 키워드
     */
    suspend fun insertHistory(keyword: String) {
        dao.insertHistory(History(keyword = keyword))

        //레코드 개수 제한, 제일 오래된 것부터 지운다.
        val count = dao.countHistory()
        if (count > 10) dao.deleteOldsByLimit(count - 10)
    }


}