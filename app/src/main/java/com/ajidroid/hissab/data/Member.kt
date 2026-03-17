package com.ajidroid.hissab.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "members")
data class Member(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var toGive: Boolean = false,
    val memberName: String,
    var totalAmount: Int = 0,

    /** Firebase UID if linked, null = local-only */
    val linkedUserId: String? = null,

    /** Member lifecycle state */
    val status: MemberStatus = MemberStatus.LOCAL_ONLY,

    /** Invite ID if sent but not yet accepted */
    val inviteId: String? = null,

    /** When connection became active */
    val connectedAt: Long? = null,

    /** Sync helper */
    val lastSyncedAt: Long? = null
)

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = Member::class,
            parentColumns = ["id"],
            childColumns = ["memberId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("memberId"),
        Index("memberId", "status"),
        Index("memberId", "type"),
        Index(value = ["memberId", "createdAt"]),
        Index("senderUserId"),
        Index("receiverUserId")
    ]
)
data class Transactions(

    @PrimaryKey
    val id: String, // 🔥 UUID (client generated)

    val memberId: Int,

    val amount: Int,

    val type: TransactionType, // BORROW / LEND

    val description: String?,

    val senderUserId: String? = null,
    val receiverUserId: String? = null,

    val status: TransactionStatus = TransactionStatus.LOCAL,

    val syncStatus: SyncStatus = SyncStatus.SYNCED,

    val createdAt: Long = System.currentTimeMillis(),

    val updatedAt: Long = System.currentTimeMillis()
)

data class MemberWithTransactions(
    @Embedded val member: Member,
    @Relation(
        parentColumn = "id",
        entityColumn = "memberId"
    )
    val transactions: List<Transactions>
)

data class MemberDetailsWithTotal(
    val memberId: Int,
    val memberName: String,
    val confirmedTotal: Int,
    val pendingTotal: Int
)

@Entity(tableName = "outbox")
data class OutboxEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val operationType: OutboxOperation,

    val payload: String, // JSON

    val createdAt: Long = System.currentTimeMillis()
)

enum class TransactionType {
    BORROW,
    LEND
}

enum class TransactionStatus {
    LOCAL,      // Only local member
    PENDING,    // Waiting for other user
    ACCEPTED,
    DECLINED
}

enum class SyncStatus {
    PENDING_SYNC,
    SYNCED,
    FAILED
}

enum class MemberStatus {
    LOCAL_ONLY,   // Just a local person
    INVITED,      // Invite sent
    CONNECTED,    // Both users connected
    BLOCKED       // Optional future
}

enum class OutboxOperation {
    CREATE_TRANSACTION,
    UPDATE_TRANSACTION
}

val membersList = mutableListOf(
    Member(0, toGive = true, memberName = "Ravi Kumar", totalAmount = 75),
    Member(1, toGive = false, memberName = "Harsh Bhandari", totalAmount = 108),
    Member(2, toGive = false, memberName = "Shankar Thakur", totalAmount = 509),
    Member(3, toGive = true, memberName = "Aijaz War", totalAmount = 234234),
    Member(4, toGive = false, memberName = "Aditya Raj", totalAmount = 534)
)