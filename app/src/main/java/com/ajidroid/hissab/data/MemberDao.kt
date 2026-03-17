package com.ajidroid.hissab.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MemberDaoQ {

    @Insert
    suspend fun insertMember(member: Member)// : Long returned id from the database

    @Insert
    suspend fun insertTransaction(transaction: Transactions) // No two dao methods should be called together from a repository they should
    // They should be done only via dao interface with @Transaction annotation to ensure atomicity

    @Insert
    suspend fun insertBulkTransactions(
        transactions: List<Transactions>
    )

    @Query("""
    UPDATE members
    SET totalAmount = 
        CASE 
            WHEN :toGive = 1 THEN totalAmount - :amount
            ELSE totalAmount + :amount
        END
    WHERE id = :memberId
""")
    suspend fun updateMemberAmount(
        memberId: Int,
        amount: Int,
        toGive: Boolean
    )

//    @Transaction
//    suspend fun splitWiseTransactions(
//        transactions: List<Transactions>
//    ) {
//        // 1️⃣ Insert a transaction for EACH selected member
//        insertBulkTransactions(transactions)
//
//        // 2️⃣ Update balance for EACH selected member
//        transactions.forEach { transaction ->
//            updateMemberAmount(
//                memberId = transaction.memberId,
//                amount = transaction.amount,
//                toGive = transaction.toGive
//            )
//        }
//    }

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

//    @Transaction
//    suspend fun addTransactionAndUpdateMember(
//        transaction: Transactions
//    ) {
//        // 1️⃣ Insert transaction
//        insertTransaction(transaction)
//
//        // 2️⃣ Update member amount based on toGive
//        if (transaction.toGive) {
//            reduceMemberAmount(
//                memberId = transaction.memberId,
//                amount = transaction.amount
//            )
//        } else {
//            increaseMemberAmount(
//                memberId = transaction.memberId,
//                amount = transaction.amount
//            )
//        }
//    }

//    @Query("SELECT * FROM members")
//    fun getAllMembers(): Flow<List<Member>> // Flow return type is already asynchronous so suspending it again would be wrong.

//    @Transaction
//    @Query("SELECT * FROM members WHERE id = :memberId")
//    fun getMemberWithTransactions(
//        memberId: Int
//    ): Flow<MemberWithTransactions?>

    @Delete
//    suspend fun deleteMember(member: Member)

//    @Query("UPDATE members SET memberName = :newName WHERE id = :memberId")
//    suspend fun renameMember(
//        memberId: Int,
//        newName: String
//    )

    @Query("SELECT * FROM members WHERE id = :memberId")
    suspend fun getMemberById(memberId: Int): Member

    @Update
    suspend fun updateMember(member: Member)
}