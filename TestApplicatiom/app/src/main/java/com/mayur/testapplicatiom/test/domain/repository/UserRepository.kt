package com.mayur.testapplicatiom.test.domain.repository

import com.mayur.testapplicatiom.test.domain.model.User
import com.mayur.testapplicatiom.utils.NetworkResult

interface UserRepository {
    suspend fun getUsers() :  NetworkResult<List<User>>
}