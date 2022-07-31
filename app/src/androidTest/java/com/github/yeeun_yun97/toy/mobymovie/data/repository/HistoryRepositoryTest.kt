package com.github.yeeun_yun97.toy.mobymovie.data.repository

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.yeeun_yun97.toy.mobymovie.data.room.HistoryDatabase
import com.github.yeeun_yun97.toy.mobymovie.tool.MobyTestUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoryRepositoryTest {
    private val context = ApplicationProvider.getApplicationContext<Application>()
    private lateinit var repo: HistoryRepository

    private val tooManyHistories = listOf(
        "a", "b", "c", "d",
        "e", "f", "g", "h",
        "i", "j", "k", "l"
    )

    @Before
    fun setup() {
        HistoryDatabase.createInstanceForTest(context)
        repo = HistoryRepository.getInstance(context)
    }

    private fun insertTooMany() =
        CoroutineScope(Dispatchers.IO).launch {
            for (history in tooManyHistories)
                repo.insertHistory(history)
        }

    @Test
    fun tooManyTest() {
        runBlocking(Dispatchers.Main) {
            val result = MobyTestUtil.getValueOrThrow(
                repo.historyList,
                ::insertTooMany
            )
            Assert.assertNotEquals(tooManyHistories.size, result.size)
            Assert.assertEquals(10, result.size)
        }
    }


}