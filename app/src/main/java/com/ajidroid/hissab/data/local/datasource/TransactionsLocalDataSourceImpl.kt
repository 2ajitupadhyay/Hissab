package com.ajidroid.hissab.data.local.datasource

import com.ajidroid.hissab.data.MemberDetailsWithTotal
import com.ajidroid.hissab.data.Transactions
import com.ajidroid.hissab.data.local.dao.TransactionsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionsLocalDataSourceImpl @Inject constructor(
    private val transactionDao: TransactionsDao
) : TransactionsLocalDataSource {

    override suspend fun insertTransaction(transaction: Transactions) {
        transactionDao.insert(transaction)
    }

    override suspend fun insertTransactions(transactions: List<Transactions>) {
        transactionDao.insertAll(transactions)
    }

    override suspend fun updateTransaction(transaction: Transactions) {
        transactionDao.update(transaction)
    }

    override fun observeTransactions(memberId: Int): Flow<List<Transactions>> =
        transactionDao.observeByMember(memberId)

    override fun observeMemberDetailsWithTotal():
            Flow<List<MemberDetailsWithTotal>> {
        return transactionDao.observeMemberDetailsWithTotal()
    }
}