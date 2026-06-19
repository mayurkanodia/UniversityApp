package com.mayur.core.network.api

import com.mayur.core.network.api.model.university.UniversityDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UniversityApi{
    /*http://universities.hipolabs.com/search?country=United%20Arab%20Emirates*/
    @GET("search")
    suspend fun getUniversities(
        @Query("country") country: String
    ): List<UniversityDto>

}