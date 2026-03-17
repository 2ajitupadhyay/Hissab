package com.ajidroid.hissab.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ajidroid.hissab.data.Member
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(member: Member): Long

    @Update
    suspend fun update(member: Member)

    @Delete
    suspend fun delete(member: Member)

    @Query("DELETE FROM members WHERE id = :memberId")
    suspend fun deleteById(memberId: Int)

    @Query("SELECT * FROM members ORDER BY id DESC")
    fun observeAll(): Flow<List<Member>>

    @Query("SELECT * FROM members WHERE id = :memberId")
    fun observeById(memberId: Int): Flow<Member?>

    @Query("SELECT * FROM members WHERE id = :memberId")
    suspend fun getMemberById(memberId: Int): Member?
}