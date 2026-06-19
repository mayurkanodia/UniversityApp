package com.mayur.testapplicatiom.test.prasentation.states

import com.mayur.testapplicatiom.test.domain.model.User

data class UserUiState (
    val products: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)