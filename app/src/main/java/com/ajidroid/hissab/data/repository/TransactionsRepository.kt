package com.ajidroid.hissab.data.repository

import com.ajidroid.hissab.data.MemberDetailsWithTotal
import com.ajidroid.hissab.data.TransactionType
import com.ajidroid.hissab.data.Transactions
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {

    fun observeMemberDetailsWithTotal(): Flow<List<MemberDetailsWithTotal>>

    fun observeTransactions(memberId: Int): Flow<List<Transactions>>

    suspend fun addTransaction(
        memberId: Int,
        amount: Int,
        type: TransactionType,
        description: String?
    )

    suspend fun splitTransaction(
        memberIds: List<Int>,
        totalAmount: Int,
        description: String?
    )
}