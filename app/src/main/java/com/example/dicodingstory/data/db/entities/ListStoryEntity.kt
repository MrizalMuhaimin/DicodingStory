package com.example.dicodingstory.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "liststory")
@Parcelize
class ListStoryEntity(
    @PrimaryKey
    val id: String ,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String,
    val lat: Float?,
    val lon: Float?,
): Parcelable
