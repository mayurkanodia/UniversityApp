package com.mayur.core.database.di

import android.content.Context
import androidx.room.Room
import com.mayur.core.database.FoodDatabase
import com.mayur.core.database.dao.UniversityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): FoodDatabase {
        return Room.databaseBuilder(
            context,
            FoodDatabase::class.java,
            "food_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(
        database: FoodDatabase
    ): UniversityDao {
        return database.universityDao()
    }

}