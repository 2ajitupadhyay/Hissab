package com.ajidroid.hissab.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Query("SELECT * FROM members")
    fun getAllMembers(): Flow<List<Member>>
//
    @Query("SELECT * FROM members WHERE id = :id")
    fun getMember(id: Int): Flow<Member>
//
//    // Update operations
//    @Update
//    suspend fun updateMember(member: Member)  // Renamed from insertTransaction to updateMember
//
//    @Update
//    suspend fun updateTransaction(transaction: Transaction)  // Added for transaction updates
//
//    // Delete operations
//    @Delete
//    suspend fun deleteMember(member: Member)
//
//    @Delete
//    suspend fun deleteTransaction(transaction: Transaction)  // Added for transaction deletion
//
//    // Additional useful queries
//    @Query("SELECT * FROM transactions WHERE memberId = :memberId")
//    fun getTransactionsForMember(memberId: Int): Flow<List<Transaction>>
//
//    @Query("DELETE FROM transactions WHERE memberId = :memberId")
//    suspend fun deleteTransactionsForMember(memberId: Int)
}