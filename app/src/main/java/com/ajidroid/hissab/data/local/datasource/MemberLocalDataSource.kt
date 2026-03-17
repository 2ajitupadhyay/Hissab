package com.ajidroid.hissab.data.local.datasource

import com.ajidroid.hissab.data.Member
import kotlinx.coroutines.flow.Flow

interface MemberLocalDataSource {

    suspend fun createMember(name: String): Long

    suspend fun updateMember(member: Member)

    suspend fun deleteMember(member: Member)

    suspend fun deleteById(memberId: Int)

    fun observeMembers(): Flow<List<Member>>

    fun observeMemberById(memberId: Int): Flow<Member?>

    suspend fun getMemberById(memberId: Int) : Member?

}