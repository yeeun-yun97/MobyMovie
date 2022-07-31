package com.github.yeeun_yun97.toy.mobymovie.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.yeeun_yun97.toy.mobymovie.data.model.History

@Dao
interface HistoryDao {
    @Query("SELECT * FROM History ORDER BY hid desc")
    fun getHistories(): LiveData<List<History>>

    @Query("SELECT count(*) FROM History")
    suspend fun countHistory():Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: History): Long

    @Query("DELETE FROM History WHERE hid IN (SELECT hid FROM History ORDER BY hid LIMIT :limit)")
    suspend fun deleteOldsByLimit(limit:Int)


}