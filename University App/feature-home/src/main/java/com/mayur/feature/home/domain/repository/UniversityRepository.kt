package com.mayur.feature.home.domain.repository

import com.mayur.core.common.result.NetworkResult
import com.mayur.feature.home.domain.model.University
import kotlinx.coroutines.flow.Flow

interface UniversityRepository {
    fun observeUniversity(): Flow<List<University>>
    suspend fun refreshUniversity() : NetworkResult<List<University>>
}