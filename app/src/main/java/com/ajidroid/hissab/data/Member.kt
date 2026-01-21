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
    val id : Int = 0,
    var toGive : Boolean = false,
    val memberName : String,
    var totalAmount : Int = 0,
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
    indices = [Index("memberId")]
)
data class Transactions(
    @PrimaryKey(autoGenerate = true)
    val transactionId: Int = 0,
    val memberId: Int,  // Foreign key to Member
    val amount: Int,
    val toGive: Boolean,
    val description: String? = null,
    val time: Long = System.currentTimeMillis()
)

data class MemberWithTransactions(
    @Embedded val member: Member,
    @Relation(
        parentColumn = "id",
        entityColumn = "memberId"
    )
    val transactions: List<Transactions>
)


val membersList = mutableListOf(
    Member(0, toGive = true, memberName = "Ravi Kumar", totalAmount = 75),
    Member(1, toGive = false, memberName = "Harsh Bhandari", totalAmount = 108),
    Member(2, toGive = false, memberName = "Shankar Thakur", totalAmount = 509),
    Member(3, toGive = true, memberName = "Aijaz War", totalAmount = 234234),
    Member(4, toGive = false, memberName = "Aditya Raj", totalAmount = 534)
)
val transaction1 = Transactions(
    amount = 94,
    toGive = false,
    description = "i have to take from Ravi",
    memberId = 1
)
val transaction2 = Transactions(
    amount = 7_000,
    toGive = true,
    description = "i have to give mess bill",
    memberId = 1
)
val transaction3 = Transactions(
    amount = 345,
    toGive = true,
    description = "this is demo text for testing",
    memberId = 1
)
val transaction4 = Transactions(
    amount = 456,
    toGive = false,
    description = "i don't know what to write anymore",
    memberId = 1
)
val transactionList = mutableListOf(
    transaction1,
    transaction3,
    transaction2,
    transaction4
)
val member1 = Member(5,true,"Shankar Thakur", 432)