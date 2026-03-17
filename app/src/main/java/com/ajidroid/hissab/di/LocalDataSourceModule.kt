package com.ajidroid.hissab.di

import com.ajidroid.hissab.data.local.datasource.MemberLocalDataSource
import com.ajidroid.hissab.data.local.datasource.MemberLocalDataSourceImpl
import com.ajidroid.hissab.data.local.datasource.TransactionsLocalDataSource
import com.ajidroid.hissab.data.local.datasource.TransactionsLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindLocalMemberDataSource(
        impl: MemberLocalDataSourceImpl
    ): MemberLocalDataSource

    @Binds
    abstract fun bindLocalTransactionDataSource(
        impl: TransactionsLocalDataSourceImpl
    ): TransactionsLocalDataSource
}