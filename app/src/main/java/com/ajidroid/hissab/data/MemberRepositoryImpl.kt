package com.ajidroid.hissab.data

import com.ajidroid.hissab.di.IoDispatcher
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MemberRepositoryImpl @Inject constructor(
    private val localDataSource: MemberLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MemberRepository {

    override suspend fun addMember(name: String) =
        withContext(ioDispatcher) {
            localDataSource.addMember(name)
        }

    override suspend fun splitWise(
        transactions: List<Transactions>
    ) = withContext(ioDispatcher) {
        localDataSource.splitWise(transactions)
    }

    override suspend fun addTransaction(
        memberId: Int,
        amount: Int,
        toGive: Boolean,
        description: String?
    ) = withContext(ioDispatcher) {
        localDataSource.addTransaction(
            memberId,
            amount,
            toGive,
            description
        )
    }

    override fun getAllMembers(): Flow<List<Member>> =
        localDataSource.getAllMembers()

    override fun getMemberWithTransactions(
        memberId: Int
    ): Flow<MemberWithTransactions?> =
        localDataSource.getMemberWithTransactions(memberId)

    override suspend fun deleteMember(member: Member) =
        withContext(ioDispatcher) {
            localDataSource.deleteMember(member)
        }

    override suspend fun renameMember(memberId: Int, newName: String) =
        withContext(ioDispatcher) {
            localDataSource.renameMember(memberId, newName)
        }
}