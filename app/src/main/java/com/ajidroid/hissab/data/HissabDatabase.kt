package com.ajidroid.hissab.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Member::class, Transaction::class], version = 1, exportSchema = false)
abstract class HissabDatabase : RoomDatabase() {

    abstract fun memberDao() : MemberDao

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
}