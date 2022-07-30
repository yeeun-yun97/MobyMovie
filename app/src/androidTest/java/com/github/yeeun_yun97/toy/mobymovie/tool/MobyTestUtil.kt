package com.github.yeeun_yun97.toy.mobymovie.tool

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import java.util.concurrent.TimeoutException

abstract class MobyTestUtil {
    companion object {
        suspend fun <T> getValueOrThrow(
            liveData: LiveData<T>,
            postFunction: (() -> Any)? = null,
            timeout: Long = 3000
        ): T {
            var result: T? = null
            liveData.observeForever {
                result = it
            }

            if (postFunction != null) {
                val postJob = postFunction()
                if (postJob is Job) postJob.join()
            }
            delay(timeout)

            if (result == null) throw TimeoutException("LiveData Update Timeout")
            else return result!!
        }
    }

}