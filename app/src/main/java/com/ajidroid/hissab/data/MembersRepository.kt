package com.ajidroid.hissab.data

import android.content.ClipData
import kotlinx.coroutines.flow.Flow

interface MembersRepository {

    fun getAllMembersStream() : Flow<List<Member>>

    fun getMemberStream(id : Int) : Flow<Member?>

    suspend fun insertMember(member: Member)

    suspend fun insertTransaction(member: Member)

    suspend fun deleteMember(member: Member)
}