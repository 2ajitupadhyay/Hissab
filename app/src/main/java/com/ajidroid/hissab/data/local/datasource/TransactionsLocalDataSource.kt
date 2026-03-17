package com.ajidroid.hissab.data.local.datasource

import com.ajidroid.hissab.data.MemberDetailsWithTotal
import com.ajidroid.hissab.data.Transactions
import kotlinx.coroutines.flow.Flow

interface TransactionsLocalDataSource {

    suspend fun insertTransaction(transaction: Transactions)

    suspend fun insertTransactions(transactions: List<Transactions>)

    suspend fun updateTransaction(transaction: Transactions)

    fun observeTransactions(memberId: Int): Flow<List<Transactions>>

    fun observeMemberDetailsWithTotal(): Flow<List<MemberDetailsWithTotal>>
}