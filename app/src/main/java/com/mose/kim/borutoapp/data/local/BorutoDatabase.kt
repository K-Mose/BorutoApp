package com.mose.kim.borutoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mose.kim.borutoapp.data.local.dao.HeroDao
import com.mose.kim.borutoapp.data.local.dao.HeroRemoteKeysDao
import com.mose.kim.borutoapp.domain.model.Hero
import com.mose.kim.borutoapp.domain.model.HeroRemoteKeys

@Database(entities = [Hero::class, HeroRemoteKeys::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class BorutoDatabase : RoomDatabase() {

    abstract fun heroDao(): HeroDao
    abstract fun heroRemoteKeysDao(): HeroRemoteKeysDao
}