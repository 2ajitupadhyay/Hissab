package com.ajidroid.hissab.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ajidroid.hissab.data.local.HissabDatabase
import com.ajidroid.hissab.data.local.dao.MemberDao
import com.ajidroid.hissab.data.local.dao.OutboxDao
import com.ajidroid.hissab.data.local.dao.TransactionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // why do we do this ::class ??
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): HissabDatabase{
        return Room.databaseBuilder(
            context,
            HissabDatabase::class.java,
            "hissab_database"
        )
            .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
            .fallbackToDestructiveMigration() // for now while developing
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideMemberDao( // Why we need module for dao but not for the repository, how dao is different
//        db: HissabDatabase
//    ): MemberDao = db.memberDao()
    @Provides
    fun provideMemberDao(db: HissabDatabase): MemberDao =
        db.memberDao()

    @Provides
    fun provideTransactionsDao(db: HissabDatabase): TransactionsDao =
        db.transactionsDao()

    @Provides
    fun provideOutboxDao(db: HissabDatabase): OutboxDao =
        db.outboxDao()
}