package com.ajidroid.hissab.data

import kotlinx.coroutines.flow.Flow

interface MemberLocalDataSource {

    suspend fun addMember(name: String)

    suspend fun splitWise(transactions: List<Transactions>)

    suspend fun addTransaction(
        memberId: Int,
        amount: Int,
        toGive: Boolean,
        description: String?
    )

    fun getAllMembers(): Flow<List<Member>>

    fun getMemberWithTransactions(
        memberId: Int
    ): Flow<MemberWithTransactions?>

    suspend fun deleteMember(member: Member)

    suspend fun renameMember(memberId: Int, newName: String)
}