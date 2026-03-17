package com.ajidroid.hissab.di

import com.ajidroid.hissab.data.repository.MemberRepository
import com.ajidroid.hissab.data.repository.MemberRepositoryImpl
import com.ajidroid.hissab.data.repository.TransactionsRepository
import com.ajidroid.hissab.data.repository.TransactionsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMemberRepository(
        impl: MemberRepositoryImpl
    ): MemberRepository

    @Binds
    abstract fun bindTransactionRepository(
        impl: TransactionsRepositoryImpl
    ): TransactionsRepository
}