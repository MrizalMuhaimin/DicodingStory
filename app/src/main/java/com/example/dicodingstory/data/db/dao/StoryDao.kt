package com.example.dicodingstory.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dicodingstory.data.db.entities.ListStoryEntity

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStory(story: List<ListStoryEntity>)


    @Query("SELECT * FROM listStory")
    fun getAllStory(): PagingSource<Int, ListStoryEntity>


    @Query("SELECT * FROM liststory")
    suspend fun findAll(): List<ListStoryEntity>


    @Query("DELETE FROM liststory")
    suspend fun deleteAll()

}