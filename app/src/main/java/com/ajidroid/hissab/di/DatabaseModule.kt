package com.ajidroid.hissab.di

import android.content.Context
import androidx.room.Room
import com.ajidroid.hissab.data.HissabDatabase
import com.ajidroid.hissab.data.MemberDao
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
            "member_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMemberDao(
        db: HissabDatabase
    ): MemberDao = db.memberDao()
}