package com.oguzhancetin.core_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Entity
data class CatImageEntity(
    @PrimaryKey
    val id: String,
    val height: Int,
    val url: String,
    val width: Int
)