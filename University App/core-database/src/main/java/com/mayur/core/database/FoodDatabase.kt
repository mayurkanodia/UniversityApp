package com.mayur.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mayur.core.database.dao.UniversityDao
import com.mayur.core.database.entity.UniversityEntity

@Database(
    entities = [UniversityEntity::class],
    version = 1,
    exportSchema = false
)

abstract class FoodDatabase : RoomDatabase() {
    abstract fun universityDao(): UniversityDao
}