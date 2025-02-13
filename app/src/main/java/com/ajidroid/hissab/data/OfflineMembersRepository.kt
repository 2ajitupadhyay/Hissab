package com.ajidroid.hissab.data

import android.content.ClipData
import kotlinx.coroutines.flow.Flow

class OfflineMembersRepository(private val memberDao: MemberDao) : MembersRepository{
    override fun getAllMembersStream(): Flow<List<Member>> = memberDao.getAllMembers()

    override fun getMemberStream(id: Int): Flow<Member?> = memberDao.getMember(id)

    override suspend fun insertMember(member: Member) = memberDao.insertMember(member)

    override suspend fun insertTransaction(member: Member) = memberDao.insertTransaction(member)

    override suspend fun deleteMember(member: Member)  = memberDao.delete(member)

}