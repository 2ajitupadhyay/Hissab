package com.ajidroid.hissab.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "members")
data class Member(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    var toGive : Boolean = false,
    val memberName : String,
    var totalAmount : Int = 0,
//    var transactionList: List<Transaction> = mutableListOf()
)

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val transactionId: Int = 0,
    val memberId: Int,  // Foreign key to Member
    val amount: Int,
    val toGive: Boolean,
    val description: String? = null
)

data class MemberWithTransactions(
    @Embedded val member: Member,
    @Relation(
        parentColumn = "id",
        entityColumn = "memberId"
    )
    val transactions: List<Transaction>
)


val membersList = mutableListOf(
    Member(0, toGive = true, memberName = "Ravi Kumar", totalAmount = 75),
    Member(1, toGive = false, memberName = "Harsh Bhandari", totalAmount = 108),
    Member(2, toGive = false, memberName = "Shankar Thakur", totalAmount = 509),
    Member(3, toGive = true, memberName = "Aijaz War", totalAmount = 234234),
    Member(4, toGive = false, memberName = "Aditya Raj", totalAmount = 534)
)
val transaction1 = Transaction(
    amount = 94,
    toGive = false,
    description = "i have to take from Ravi",
    memberId = 1
)
val transaction2 = Transaction(
    amount = 7_000,
    toGive = true,
    description = "i have to give mess bill",
    memberId = 1
)
val transaction3 = Transaction(
    amount = 345,
    toGive = true,
    description = "this is demo text for testing",
    memberId = 1
)
val transaction4 = Transaction(
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