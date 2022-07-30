package com.github.yeeun_yun97.toy.mobymovie.data.dataSource

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.yeeun_yun97.toy.mobymovie.data.model.History
import com.github.yeeun_yun97.toy.mobymovie.data.room.HistoryDao
import com.github.yeeun_yun97.toy.mobymovie.data.room.HistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HistoryDaoTest {
    private val context: Context = ApplicationProvider.getApplicationContext<Application>()
    private val database =
        Room.inMemoryDatabaseBuilder(context, HistoryDatabase::class.java).build()
    private val dao: HistoryDao = database.getDao()

    @Before
    fun setup() {
        runBlocking(Dispatchers.IO) {
            dao.insertHistory(History(keyword = "Test1"))
            dao.insertHistory(History(keyword = "Test2"))
            dao.insertHistory(History(keyword = "Test3"))
        }
    }

    @Test
    fun insertAndCountHistoryTest() {
        runBlocking(Dispatchers.IO) {
            Assert.assertEquals(3, dao.countHistory())
        }
    }

    @Test
    fun deleteDuplicatesTest() {
        runBlocking(Dispatchers.IO) {
            dao.deleteDuplicates("Test1")
            Assert.assertEquals(2, dao.countHistory())

            dao.deleteDuplicates("Test2")
            Assert.assertEquals(1, dao.countHistory())
        }
    }

    @Test
    fun deleteOldsByLimitTest() {
        runBlocking(Dispatchers.IO) {
            dao.deleteOldsByLimit(3)
            Assert.assertEquals(0, dao.countHistory())
        }
    }


}