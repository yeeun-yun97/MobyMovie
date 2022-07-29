package com.github.yeeun_yun97.toy.mobymovie.data.retrofit

import com.github.yeeun_yun97.toy.mobymovie.data.model.NaverSearchResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface NaverMovieService {
    companion object {
        private const val BASE_URL = "https://openapi.naver.com/"
        private lateinit var service: NaverMovieService

        fun getInstance(): NaverMovieService {
            if (!this::service.isInitialized) {
                service = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(NaverMovieService::class.java)
            }
            return service
        }
    }

    @Headers(
        "X-Naver-Client-Id:RohV421ZcW0SdOPby2ZY",
        "X-Naver-Client-Secret:PuH87jAjJP"
    )
    @GET("/v1/search/movie.json")
    suspend fun searchMovie(
        @Query("query", encoded = true) query: String,
        @Query("start", encoded = true) start: Int,
        @Query("display", encoded = true) display: Int = 20,

    ): Response<NaverSearchResponse>


}