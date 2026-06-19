package com.mayur.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "university")
data class UniversityEntity(
    @PrimaryKey
    val name: String,
    val country: String
)
