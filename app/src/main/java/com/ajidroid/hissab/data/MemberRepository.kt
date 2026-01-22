package com.ajidroid.hissab.data

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class MemberRepository @Inject constructor(
    private val dao: MemberDao
) {// need to add more methods like discard a transaction; the transaction will be there but it will not provide value to the total amount
    // OR edit a transaction, but if you can edit a transaction then it could also lead to fraud; so there should be a maximum time to
    // edit a transaction or you can edit only the last transaction, not the above it.

    // Insert member
    suspend fun addMember(name: String) {  // we can also return int value from the added the member to navigate to add transactions
        dao.insertMember(Member(memberName = name))
    }

    suspend fun splitWise(
        transactions: List<Transactions>
    ) {
        dao.splitWiseTransactions(transactions)
    }

    // Insert transaction
    suspend fun addTransaction(
        memberId: Int,
        amount: Int,
        toGive: Boolean,
        description: String?
    ) {
        dao.addTransactionAndUpdateMember(
            Transactions(
                memberId = memberId,
                amount = amount,
                toGive = toGive,
                description = description,
                time = System.currentTimeMillis()
            )
        )
    }

    // Get all members
    fun getAllMembers(): Flow<List<Member>> =
        dao.getAllMembers()

    // Get member with transactions
    fun getMemberTransactions(
        memberId: Int
    ): Flow<MemberWithTransactions?> {
        return dao.getMemberWithTransactions(memberId)
    }

    suspend fun deleteMember(member: Member) =
        dao.deleteMember(member)

    // Rename member
    suspend fun renameMember(memberId: Int, newName: String) =
        dao.renameMember(memberId, newName)
}