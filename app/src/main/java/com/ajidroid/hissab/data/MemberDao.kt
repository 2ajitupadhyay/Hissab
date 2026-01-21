package com.ajidroid.hissab.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

    @Insert
    suspend fun insertMember(member: Member)// : Long returned id from the database

    @Insert
    suspend fun insertTransaction(transaction: Transactions) // No two dao methods should be called together from a repository they should
    // They should be done only via dao interface with @Transaction annotation to ensure atomicity

    // Reduce member amount
    @Query("""
        UPDATE members 
        SET totalAmount = totalAmount - :amount 
        WHERE id = :memberId
    """)
    suspend fun reduceMemberAmount(
        memberId: Int,
        amount: Int
    )

    // Increase member amount
    @Query("""
        UPDATE members 
        SET totalAmount = totalAmount + :amount 
        WHERE id = :memberId
    """)
    suspend fun increaseMemberAmount(
        memberId: Int,
        amount: Int
    )

    @Transaction
    suspend fun addTransactionAndUpdateMember(
        transaction: Transactions
    ) {
        // 1️⃣ Insert transaction
        insertTransaction(transaction)

        // 2️⃣ Update member amount based on toGive
        if (transaction.toGive) {
            reduceMemberAmount(
                memberId = transaction.memberId,
                amount = transaction.amount
            )
        } else {
            increaseMemberAmount(
                memberId = transaction.memberId,
                amount = transaction.amount
            )
        }
    }

    @Query("SELECT * FROM members")
    fun getAllMembers(): Flow<List<Member>> // Flow return type is already asynchronous so suspending it again would be wrong.

    @Transaction
    @Query("SELECT * FROM members WHERE id = :memberId")
    fun getMemberWithTransactions(
        memberId: Int
    ): Flow<MemberWithTransactions?>

    @Delete
    suspend fun deleteMember(member: Member)

    @Query("UPDATE members SET memberName = :newName WHERE id = :memberId")
    suspend fun renameMember(
        memberId: Int,
        newName: String
    )
}