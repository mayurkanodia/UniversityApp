package com.mayur.feature.home.data.repository


import android.util.Log
import com.mayur.core.common.result.NetworkResult
import com.mayur.core.database.dao.UniversityDao
import com.mayur.core.network.api.UniversityApi
import com.mayur.core.network.api.model.university.UniversityDto
import com.mayur.core.network.connectivity.NetworkChecker
import com.mayur.core.network.util.retryIO
import com.mayur.core.network.util.safeApiCall
import com.mayur.feature.home.data.mapper.toDomain
import com.mayur.feature.home.data.mapper.toEntity
import com.mayur.feature.home.domain.model.University
import com.mayur.feature.home.domain.repository.UniversityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UniversityRepositoryImpl @Inject constructor(
    private val api: UniversityApi,
    private val dao: UniversityDao,
    private val networkChecker: NetworkChecker
) : UniversityRepository {

    override fun observeUniversity(): Flow<List<University>> {
        return dao.observeUniversity().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun refreshUniversity(): NetworkResult<List<University>> {

        if (!networkChecker.isConnected()) {
            return NetworkResult.Error(
                Exception("No Internet")
            )
        }
        val response: NetworkResult<List<UniversityDto>> =
            safeApiCall {
                retryIO {
                    api.getUniversities("United Arab Emirates")
                }
            }
        Log.d("response.......",response.toString())
        return when (response) {
            is NetworkResult.Success -> {
                val entities = response.data.map { it.toEntity() }
                dao.replaceUniversity(entities)
                NetworkResult.Success(entities.map { it.toDomain() })
            }

            is NetworkResult.Error -> {
                NetworkResult.Error(response.exception)
            }

            is NetworkResult.Failure -> {
                NetworkResult.Failure(response.code, response.message)
            }

        }

    }
}