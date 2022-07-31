package com.github.yeeun_yun97.toy.mobymovie.data.repository

import com.github.yeeun_yun97.toy.mobymovie.tool.MobyTestUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MovieRepositoryTest {
    private val repo = MovieRepository.getInstance()

    private fun searchStart() =
        CoroutineScope(Dispatchers.IO).launch {
            repo.searchByKeywordAndPage("슈퍼맨", 1)
        }

    private fun loadNext() =
        CoroutineScope(Dispatchers.IO).launch {
            repo.loadNextOfPrevSearch()
        }

    @Test
    fun searchStartTest() {
        runBlocking(Dispatchers.Main) {
            val result = MobyTestUtil.getValueOrThrow(repo.movieList, ::searchStart)
            Assert.assertEquals(false, result.isEmpty())
        }
    }

    @Test
    fun loadNextTest() {
        runBlocking(Dispatchers.Main) {
            val result = MobyTestUtil.getValueOrThrow(repo.movieList, ::searchStart).size
            Assert.assertEquals(20, result)

            val resultMore = MobyTestUtil.getValueOrThrow(repo.movieList, ::loadNext,5000).size
            Assert.assertEquals(true, result < resultMore)
        }
    }


}