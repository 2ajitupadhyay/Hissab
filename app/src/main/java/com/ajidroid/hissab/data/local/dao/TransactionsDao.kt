package com.ajidroid.hissab.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ajidroid.hissab.data.MemberDetailsWithTotal
import com.ajidroid.hissab.data.SyncStatus
import com.ajidroid.hissab.data.Transactions
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transactions)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<Transactions>)

    @Update
    suspend fun update(transaction: Transactions)

    @Query("SELECT * FROM transactions WHERE memberId = :memberId ORDER BY createdAt DESC")
    fun observeByMember(memberId: Int): Flow<List<Transactions>>

    @Query("SELECT * FROM transactions WHERE syncStatus = :status")
    suspend fun getBySyncStatus(status: SyncStatus): List<Transactions>

    @Query(
        """
    SELECT 
        m.id AS memberId,
        m.memberName AS memberName,

        COALESCE(SUM(
            CASE
                WHEN t.status IN ('LOCAL','ACCEPTED') AND t.type = 'LEND'
                    THEN t.amount
                WHEN t.status IN ('LOCAL','ACCEPTED') AND t.type = 'BORROW'
                    THEN -t.amount
                ELSE 0
            END
        ),0) AS confirmedTotal,

        COALESCE(SUM(
            CASE
                WHEN t.status = 'PENDING' AND t.type = 'LEND'
                    THEN t.amount
                WHEN t.status = 'PENDING' AND t.type = 'BORROW'
                    THEN -t.amount
                ELSE 0
            END
        ),0) AS pendingTotal

    FROM members m
    LEFT JOIN transactions t
        ON t.memberId = m.id

    GROUP BY m.id
    ORDER BY m.id DESC
    """
    )
    fun observeMemberDetailsWithTotal(): Flow<List<MemberDetailsWithTotal>>
}