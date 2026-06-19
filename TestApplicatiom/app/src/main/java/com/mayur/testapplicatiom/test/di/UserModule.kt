package com.mayur.testapplicatiom.test.di

import com.mayur.testapplicatiom.test.data.repository.UserRepositoryImp
import com.mayur.testapplicatiom.test.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Binds
    @Singleton
    abstract fun bindRealRepository(
        impl: UserRepositoryImp
    ): UserRepository
}