package com.ajidroid.hissab.data.local.datasource

import com.ajidroid.hissab.data.Member
import com.ajidroid.hissab.data.local.dao.MemberDao
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class MemberLocalDataSourceImpl @Inject constructor(
    private val memberDao: MemberDao
) : MemberLocalDataSource {

    override suspend fun createMember(name: String): Long {
        return memberDao.insert(
            Member(memberName = name.trim())
        )
    }

    override suspend fun updateMember(member: Member) {
        memberDao.update(member)
    }

    override suspend fun deleteMember(member: Member) {
        memberDao.delete(member)
    }

    override suspend fun deleteById(memberId: Int) {
        memberDao.deleteById(memberId)
    }

    override fun observeMembers(): Flow<List<Member>> =
        memberDao.observeAll()

    override fun observeMemberById(memberId: Int): Flow<Member?> =
        memberDao.observeById(memberId)

    override suspend fun getMemberById(memberId: Int): Member? {
        return memberDao.getMemberById(memberId)
    }
}