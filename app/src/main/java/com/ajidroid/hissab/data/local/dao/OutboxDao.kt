package com.ajidroid.hissab.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ajidroid.hissab.data.OutboxEntity

@Dao
interface OutboxDao {

    @Insert
    suspend fun insert(outbox: OutboxEntity)

    @Query("SELECT * FROM outbox ORDER BY createdAt ASC")
    suspend fun getAll(): List<OutboxEntity>

    @Delete
    suspend fun delete(outbox: OutboxEntity)
}