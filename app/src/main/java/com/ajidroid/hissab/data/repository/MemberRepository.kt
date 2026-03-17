package com.ajidroid.hissab.data.repository

import com.ajidroid.hissab.data.Member
import kotlinx.coroutines.flow.Flow

interface MemberRepository {

    fun observeMembers(): Flow<List<Member>>

    fun observeMemberById(memberId: Int): Flow<Member?>

//    suspend fun getMemberById(memberId: Int): Member?

    suspend fun addMember(name: String)

    suspend fun renameMember(memberId: Int, newName: String)

    suspend fun deleteMember(member: Member)

    suspend fun deleteById(memberId: Int)
}