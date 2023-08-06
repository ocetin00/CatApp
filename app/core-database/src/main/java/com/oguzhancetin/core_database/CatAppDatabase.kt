package com.oguzhancetin.core_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oguzhancetin.core_database.entity.CatImageEntity

@Database(
    entities = [CatImageEntity::class],
    version = 2,
    exportSchema = true
)
abstract class CatAppDatabase : RoomDatabase() {

    abstract fun catDao(): CatDao

}
