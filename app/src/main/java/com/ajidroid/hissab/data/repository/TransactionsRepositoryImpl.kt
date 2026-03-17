package com.ajidroid.hissab.data.repository

import com.ajidroid.hissab.data.MemberDetailsWithTotal
import com.ajidroid.hissab.data.SyncStatus
import com.ajidroid.hissab.data.TransactionStatus
import com.ajidroid.hissab.data.TransactionType
import com.ajidroid.hissab.data.Transactions
import com.ajidroid.hissab.data.local.datasource.MemberLocalDataSource
import com.ajidroid.hissab.data.local.datasource.TransactionsLocalDataSource
import com.ajidroid.hissab.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val transactionLocal: TransactionsLocalDataSource,
    private val memberLocal: MemberLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : TransactionsRepository {

    override fun observeMemberDetailsWithTotal():
            Flow<List<MemberDetailsWithTotal>> {
        return transactionLocal.observeMemberDetailsWithTotal()
    }

    override fun observeTransactions(memberId: Int): Flow<List<Transactions>> =
        transactionLocal.observeTransactions(memberId)

    override suspend fun addTransaction(
        memberId: Int,
        amount: Int,
        type: TransactionType,
        description: String?
    ) = withContext(ioDispatcher) {

        val transaction = Transactions(
            id = UUID.randomUUID().toString(),
            memberId = memberId,
            amount = amount,
            type = type,
            description = description,
            status = TransactionStatus.LOCAL,
            syncStatus = SyncStatus.SYNCED
        )

        transactionLocal.insertTransaction(transaction)
    }

    override suspend fun splitTransaction(
        memberIds: List<Int>,
        totalAmount: Int,
        description: String?
    ) = withContext(ioDispatcher) {

        val count = memberIds.size
        if (count == 0) return@withContext

        val base = totalAmount / count
        val remainder = totalAmount % count

        val transactions = memberIds.mapIndexed { index, memberId ->
            Transactions(
                id = UUID.randomUUID().toString(),
                memberId = memberId,
                amount = base + if (index == 0) remainder else 0,
                type = TransactionType.BORROW,
                description = description,
                status = TransactionStatus.LOCAL,
                syncStatus = SyncStatus.SYNCED
            )
        }

        transactionLocal.insertTransactions(transactions)
    }
}