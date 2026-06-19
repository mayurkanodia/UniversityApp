package com.mayur.feature.home.domain.usecase

import com.mayur.feature.home.domain.repository.UniversityRepository
import javax.inject.Inject

class GetUniversityUseCase @Inject constructor(
    private val repository: UniversityRepository
) {
    operator fun invoke() =
        repository.observeUniversity()
}