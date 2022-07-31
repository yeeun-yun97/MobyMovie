package com.github.yeeun_yun97.toy.mobymovie.viewModel

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.yeeun_yun97.toy.mobymovie.data.room.HistoryDatabase
import com.github.yeeun_yun97.toy.mobymovie.tool.MobyTestUtil
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchViewModelTest {
    private val application =
        ApplicationProvider.getApplicationContext() as Application
    private val viewModel = SearchViewModel(application)

    @Before
    fun setup() {
        HistoryDatabase.createInstanceForTest(application.applicationContext)
    }
    private fun onError(code: Int) {}
    private fun onFinish() {}

    private fun saveKeywordToHistory() =
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.bindingKeyword.postValue("슈퍼맨")
            delay(1000)
            viewModel.saveKeywordToHistory().join()
        }

    private fun searchStart() =
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.bindingKeyword.postValue("슈퍼맨")
            delay(1000)
            viewModel.searchStart(::onError).join()
        }

    private fun loadMore() =
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.bindingKeyword.postValue("슈퍼맨")
            delay(1000)
            viewModel.loadMore(::onError, ::onFinish).join()
        }

    @Test
    fun saveKeywordToHistoryTest() {
        runBlocking(Dispatchers.Main) {
            val result = MobyTestUtil.getValueOrThrow(
                viewModel.historyList,
                ::saveKeywordToHistory
            )
            Assert.assertEquals(1, result.size)
            Assert.assertEquals("슈퍼맨", result[0].keyword)
        }
    }

    @Test
    fun searchStartTest() {
        runBlocking(Dispatchers.Main) {
            val result = MobyTestUtil.getValueOrThrow(
                viewModel.movieList,
                ::searchStart
            )
            Assert.assertNotEquals(0, result.size)
        }
    }

    @Test
    fun loadMoreTest() {
        runBlocking(Dispatchers.Main) {
            val result = MobyTestUtil.getValueOrThrow(
                viewModel.movieList,
                ::searchStart
            ).size

            val resultMore = MobyTestUtil.getValueOrThrow(
                viewModel.movieList,
                ::loadMore
            ).size

            Assert.assertEquals(true, resultMore > result)
        }
    }


}