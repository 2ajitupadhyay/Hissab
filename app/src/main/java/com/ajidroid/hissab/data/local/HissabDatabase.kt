package com.ajidroid.hissab.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ajidroid.hissab.data.Member
import com.ajidroid.hissab.data.OutboxEntity
import com.ajidroid.hissab.data.Transactions
import com.ajidroid.hissab.data.local.dao.MemberDao
import com.ajidroid.hissab.data.local.dao.OutboxDao
import com.ajidroid.hissab.data.local.dao.TransactionsDao

@Database(
    entities = [
        Member::class,
        Transactions::class,
        OutboxEntity::class
    ],
    version = 2
)
abstract class HissabDatabase : RoomDatabase(){
    abstract fun memberDao(): MemberDao

    abstract fun transactionsDao(): TransactionsDao

    abstract fun outboxDao(): OutboxDao
}

//@Database(entities = [Member::class, Transactions::class], version = 1, exportSchema = false)
//abstract class HissabDatabase : RoomDatabase() {
//
//    abstract fun memberDao() : MemberDao

//    companion object{
//
//        @Volatile
//        private var Instance : HissabDatabase? = null
//
//        fun getDatabase(context: Context) : HissabDatabase {
//
//            return Instance ?: synchronized(this){
//                Room.databaseBuilder(context, HissabDatabase::class.java, "member_database")
//                    .fallbackToDestructiveMigration()
//                    .build()
//                    .also { Instance = it }
//            }
//        }
//    }
//}