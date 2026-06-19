package com.mayur.feature.home.data.mapper

import com.mayur.core.database.entity.UniversityEntity
import com.mayur.core.network.api.model.university.UniversityDto
import com.mayur.feature.home.domain.model.University

fun UniversityDto.toEntity(): UniversityEntity {
    return UniversityEntity(
        name = name,
        country = country
    )
}

fun UniversityEntity.toDomain(): University {
        return University(
        name = name,
        country = country
    )
}