package com.ajidroid.hissab.di

import com.ajidroid.hissab.auth.data.dataSource.AuthDataSource
import com.ajidroid.hissab.auth.data.dataSource.FirebaseAuthDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthDataSourceModule {

    @Binds
    abstract fun bindAuthDataSource(
        impl: FirebaseAuthDataSource
    ): AuthDataSource
}