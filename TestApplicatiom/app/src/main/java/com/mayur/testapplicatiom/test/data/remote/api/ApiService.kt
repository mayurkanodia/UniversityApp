package com.mayur.testapplicatiom.test.data.remote.api

import com.mayur.testapplicatiom.test.data.remote.dto.UserListItem
import retrofit2.http.GET

interface ApiService {
   // users
    @GET("users")
    suspend fun getUsers(): List<UserListItem>
}