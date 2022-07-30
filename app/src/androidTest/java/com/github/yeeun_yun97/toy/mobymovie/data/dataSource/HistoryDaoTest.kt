package com.github.yeeun_yun97.toy.mobymovie.data.dataSource

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.yeeun_yun97.toy.mobymovie.data.model.History
import com.github.yeeun_yun97.toy.mobymovie.data.room.HistoryDao
import com.github.yeeun_yun97.toy.mobymovie.data.room.HistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoryDaoTest {
    private val context: Context = ApplicationProvider.getApplicationContext<Application>()
    private lateinit var dao: HistoryDao

    @Before
    fun setup() {
        runBlocking(Dispatchers.IO) {
            HistoryDatabase.createInstanceForTest(context)
            dao = HistoryDatabase.getInstance(context).getDao()

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