package com.ajidroid.hissab.data

import android.content.ClipData.Item
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

    @Query("SELECT * from members")
    fun getAllMembers() : Flow<List<Member>>

    @Query("SELECT * from members WHERE id = :id")
    fun getMember(id : Int) : Flow<Member>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMember(member : Member)

    @Update
    suspend fun insertTransaction(member: Member)

    @Delete
    suspend fun delete(member : Member)
}