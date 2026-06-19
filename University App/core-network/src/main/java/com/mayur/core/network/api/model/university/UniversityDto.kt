package com.mayur.core.network.api.model.university

data class UniversityDto(
    val name: String,
    val country: String,
    val web_pages: List<String>,
    val domains: List<String>
)