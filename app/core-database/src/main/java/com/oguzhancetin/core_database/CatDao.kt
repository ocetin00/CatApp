package com.oguzhancetin.core_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oguzhancetin.core_database.entity.CatImageEntity

@Dao
interface CatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatImageList(pokemonList: List<CatImageEntity>)

    @Query("SELECT * FROM CatImageEntity")
    suspend fun getAllCatImages(): List<CatImageEntity>

}