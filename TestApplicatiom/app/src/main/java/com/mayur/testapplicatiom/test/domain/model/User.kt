package com.mayur.testapplicatiom.test.domain.model

import com.mayur.testapplicatiom.test.data.remote.dto.Address
import com.mayur.testapplicatiom.test.data.remote.dto.Company

data class User(
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)