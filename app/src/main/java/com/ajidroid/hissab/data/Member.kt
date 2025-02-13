package com.ajidroid.hissab.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "members")
data class Member(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    var toGive : Boolean,
    val memberName : String,
    var totalAmount : Int,
    var transactionList: List<Transaction> = mutableListOf()
)

data class Transaction(
    val amount : Int,
    val toGive : Boolean,
    val description : String? = null
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
    description = "i have to take from Ravi"
)
val transaction2 = Transaction(
    amount = 7_000,
    toGive = true,
    description = "i have to give mess bill"
)
val transaction3 = Transaction(
    amount = 345,
    toGive = true,
    description = "this is demo text for testing"
)
val transaction4 = Transaction(
    amount = 456,
    toGive = false,
    description = "i don't know what to write anymore"
)
val transactionList = mutableListOf(
    transaction1,
    transaction3,
    transaction2,
    transaction4
)
val member1 = Member(5,true,"Shankar Thakur", 432, transactionList)