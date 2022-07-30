package com.github.yeeun_yun97.toy.mobymovie.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.yeeun_yun97.toy.mobymovie.data.model.History

@Database(
    version = 1,
    entities = [History::class]
)
abstract class HistoryDatabase : RoomDatabase() {
    companion object {
        private lateinit var db: HistoryDatabase
        fun getInstance(applicationContext:Context): HistoryDatabase {
            if (!this::db.isInitialized) {
                db = Room.databaseBuilder(
                    applicationContext,
                    HistoryDatabase::class.java, "mobyDatabase"
                ).build()
            }
            return db
        }
    }

    abstract fun getDao(): HistoryDao


}