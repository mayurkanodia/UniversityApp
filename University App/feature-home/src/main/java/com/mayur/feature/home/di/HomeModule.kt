package com.mayur.feature.home.di

import com.mayur.feature.home.data.repository.UniversityRepositoryImpl
import com.mayur.feature.home.domain.repository.UniversityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {
    @Binds
    @Singleton
    abstract fun bindRealRepository(
        impl: UniversityRepositoryImpl
    ): UniversityRepository

}