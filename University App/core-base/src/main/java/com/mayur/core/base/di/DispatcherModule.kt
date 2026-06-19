package com.mayur.core.base.di

import com.mayur.core.common.dispatcher.DefaultDispatcherProvider
import com.mayur.core.common.dispatcher.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider():
            DispatcherProvider {
        return DefaultDispatcherProvider()
    }
}


// It will not work because Hilt cannot inject
// the implementation of DispatcherProvider
//DefaultDispatcherProvider
// DefaultDispatcherProvider @Inject constructor is not possible because it has no dependencies to inject, and Hilt cannot create an instance of it without a constructor.
// Therefore, we need to provide it using a @Provides method in the module, as shown above.
/*
@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {

    @Binds
    @Singleton
    abstract fun bindDispatcherProvider(
        impl: DefaultDispatcherProvider
    ): DispatcherProvider
}*/
