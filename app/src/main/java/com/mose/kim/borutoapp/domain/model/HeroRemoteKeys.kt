package com.mose.kim.borutoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mose.kim.borutoapp.util.Constants

@Entity(tableName = Constants.HERO_REMOTE_KEYS_DATABASE_TABLE)
data class HeroRemoteKeys (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?
)

