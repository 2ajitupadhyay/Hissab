package com.ajidroid.hissab.data

import android.content.ClipData
import kotlinx.coroutines.flow.Flow

//class OfflineMembersRepository(private val memberDao: MemberDao) : MembersRepository{
//    override fun getAllMembersStream(): Flow<List<Member>> = memberDao.getAllMembers()
//
//    override fun getMemberStream(id: Int): Flow<Member?> = memberDao.getMember(id)
//
//    override suspend fun insertMember(member: Member) = memberDao.insertMember(member)
//
//    override suspend fun insertTransaction(member: Member) = memberDao.insertTransaction(member)
//
//    override suspend fun deleteMember(member: Member)  = memberDao.delete(member)
//
//}
class OfflineMembersRepository(
    private val memberDao: MemberDao
) : MembersRepository {

    // Get all members
    override fun getAllMembersStream(): Flow<List<Member>> =
        memberDao.getAllMembers()

//    // Get specific member
//    override fun getMemberStream(id: Int): Flow<Member?> =
//        memberDao.getMember(id)
//
//    // Get member with their transactions
//    override fun getMemberWithTransactionsStream(id: Int): Flow<MemberWithTransactions?> =
//        memberDao.getMembersWithTransactions()
//            .map { membersWithTransactions ->
//                membersWithTransactions.firstOrNull { it.member.id == id }
//            }
//
//    // Get all members with their transactions
//
//
//
//    // Member CRUD operations
//    override suspend fun insertMember(member: Member): Long =
//        memberDao.insertMember(member)
//
//    override suspend fun updateMember(member: Member) =
//        memberDao.updateMember(member)
//
//    override suspend fun deleteMember(member: Member) =
//        memberDao.deleteMember(member)
//
//    // Transaction operations
//    override suspend fun insertTransaction(transaction: Transaction) =
//        memberDao.insertTransaction(transaction)
//
//    override suspend fun updateTransaction(transaction: Transaction) =
//        memberDao.updateTransaction(transaction)
//
//    override suspend fun deleteTransaction(transaction: Transaction) =
//        memberDao.deleteTransaction(transaction)
//
//    // Bulk operations
//    override suspend fun insertMemberWithTransactions(
//        member: Member,
//        transactions: List<Transaction>
//    ) {
//        // Insert member and get generated ID
//        val memberId = insertMember(member)
//
//        // Insert all transactions with the memberId
//        transactions.forEach { transaction ->
//            insertTransaction(transaction.copy(memberId = memberId.toInt()))
//        }
//    }
//
//    override suspend fun deleteMemberWithTransactions(memberId: Int) {
//        // First delete all transactions for this member
//        memberDao.deleteTransactionsForMember(memberId)
//
//        // Then delete the member
//        getMemberStream(memberId).collect { member ->
//            member?.let { deleteMember(it) }
//        }
//    }
}