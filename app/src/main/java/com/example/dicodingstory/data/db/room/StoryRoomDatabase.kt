package com.example.dicodingstory.data.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dicodingstory.data.db.dao.KeyStoryDao
import com.example.dicodingstory.data.db.dao.StoryDao
import com.example.dicodingstory.data.db.entities.KeyEntity
import com.example.dicodingstory.data.db.entities.ListStoryEntity

@Database(
    entities = [ListStoryEntity::class, KeyEntity::class],
    version = 1,
    exportSchema = false
)

abstract class StoryRoomDatabase : RoomDatabase(){

    abstract fun StoryDao() : StoryDao
    abstract fun KeyStoryDao() : KeyStoryDao

    companion object {

        @Volatile
        private var INSTANCE: StoryRoomDatabase? = null


        @JvmStatic
        fun getDatabase(context: Context): StoryRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryRoomDatabase::class.java, "listStory.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}
