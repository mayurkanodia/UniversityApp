package com.mayur.testapplicatiom.test.domain.usecase


import com.mayur.testapplicatiom.test.domain.model.User
import com.mayur.testapplicatiom.test.domain.repository.UserRepository
import com.mayur.testapplicatiom.utils.NetworkResult
import javax.inject.Inject

class GetUserUseCase  @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() : NetworkResult<List<User>> {
        return repository.getUsers()
    }
}