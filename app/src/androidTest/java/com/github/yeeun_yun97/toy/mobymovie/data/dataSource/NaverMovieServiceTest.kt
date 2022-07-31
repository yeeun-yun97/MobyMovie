package com.github.yeeun_yun97.toy.mobymovie.data.dataSource

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.yeeun_yun97.toy.mobymovie.data.retrofit.NaverMovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NaverMovieServiceTest {
    private val service = NaverMovieService.getInstance()

    @Test
    fun searchMovieSuccessfulTest(){
        runBlocking(Dispatchers.IO) {
            val response = service.searchMovie("Test",1)
            Assert.assertEquals(response.code(),200)
        }
    }


}