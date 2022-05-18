package com.example.dicodingstory.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "keys")
@Parcelize
data class KeyEntity (
    @PrimaryKey
    val id: String,
    val prevKey: Int?,
    val nextKey: Int?
):Parcelable