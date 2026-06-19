package com.mayur.testapplicatiom.test.data.repository

import com.mayur.testapplicatiom.test.data.mappers.toEntity
import com.mayur.testapplicatiom.test.data.remote.api.ApiService
import com.mayur.testapplicatiom.test.data.remote.dto.UserListItem
import com.mayur.testapplicatiom.test.domain.model.User
import com.mayur.testapplicatiom.test.domain.repository.UserRepository
import com.mayur.testapplicatiom.utils.NetworkResult
import com.mayur.testapplicatiom.utils.safeApiCall
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(private val apiService: ApiService) : UserRepository {
    override suspend fun getUsers(): NetworkResult<List<User>> {
        val response: NetworkResult<List<UserListItem>> =
            safeApiCall {
                apiService.getUsers()
            }
        return when (response) {
            is NetworkResult.Success -> {
                val entities = response.data.map { it.toEntity() }
                NetworkResult.Success(entities)
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