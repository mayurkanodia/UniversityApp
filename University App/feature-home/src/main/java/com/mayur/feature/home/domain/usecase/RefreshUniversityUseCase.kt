package com.mayur.feature.home.domain.usecase

import com.mayur.core.common.result.NetworkResult
import com.mayur.feature.home.domain.model.University
import com.mayur.feature.home.domain.repository.UniversityRepository
import javax.inject.Inject

class RefreshUniversityUseCase @Inject constructor(
    private val repository: UniversityRepository
) {
    suspend operator fun invoke() : NetworkResult<List<University>> {
        return repository.refreshUniversity()
    }
}