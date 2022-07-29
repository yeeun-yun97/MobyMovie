package com.github.yeeun_yun97.toy.mobymovie.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["keyword"], unique = true)])
data class History(
    @PrimaryKey(autoGenerate = true)
    val hid: Long = 0L,
    val keyword: String
)