package com.ajidroid.hissab.data

import android.content.ClipData
import kotlinx.coroutines.flow.Flow

//interface MembersRepository {
//
//    fun getAllMembersStream() : Flow<List<Member>>
//
//    fun getMemberStream(id : Int) : Flow<Member?>
//
//    suspend fun insertMember(member: Member)
//
//    suspend fun insertTransaction(member: Member)
//
//    suspend fun deleteMember(member: Member)
//}

interface MembersRepository {
    // Get all members
    fun getAllMembersStream(): Flow<List<Member>>

//    // Get a specific member
//    fun getMemberStream(id: Int): Flow<Member?>
//
//    // Get member with transactions
//    fun getMemberWithTransactionsStream(id: Int): Flow<MemberWithTransactions?>
//
//    // Get all members with their transactions
//    fun getAllMembersWithTransactionsStream(): Flow<List<MemberWithTransactions>>
//
//    // Member operations
//    suspend fun insertMember(member: Member): Long
//    suspend fun updateMember(member: Member)
//    suspend fun deleteMember(member: Member)
//
//    // Transaction operations
//    suspend fun insertTransaction(transaction: Transaction)
//    suspend fun updateTransaction(transaction: Transaction)
//    suspend fun deleteTransaction(transaction: Transaction)
//
//    // Bulk operations
//    suspend fun insertMemberWithTransactions(member: Member, transactions: List<Transaction>)
//    suspend fun deleteMemberWithTransactions(memberId: Int)
}