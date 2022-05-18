package com.example.dicodingstory.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dicodingstory.data.db.entities.KeyEntity

@Dao
interface KeyStoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(remoteKey: List<KeyEntity>)


    @Query("SELECT * FROM keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): KeyEntity?


    @Query("DELETE FROM keys")
    suspend fun deleteKeys()
}