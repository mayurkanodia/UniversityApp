package com.mayur.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.mayur.core.database.entity.UniversityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UniversityDao  {

    @Query("SELECT * FROM university")
    fun observeUniversity(): Flow<List<UniversityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUniversity(
        products: List<UniversityEntity>
    )

    @Query("DELETE FROM university")
    suspend fun clearUniversity()

    @Transaction
    suspend fun replaceUniversity(
        products: List<UniversityEntity>
    ) {
        clearUniversity()
        insertUniversity(products)
    }
}