package com.ajidroid.hissab.data.repository

import com.ajidroid.hissab.data.Member
import com.ajidroid.hissab.data.local.datasource.MemberLocalDataSource
import com.ajidroid.hissab.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberLocal: MemberLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MemberRepository {

    override fun observeMembers(): Flow<List<Member>> =
        memberLocal.observeMembers()

    override fun observeMemberById(memberId: Int): Flow<Member?> =
        memberLocal.observeMemberById(memberId)

    override suspend fun addMember(name: String): Unit =
        withContext(ioDispatcher) {
            memberLocal.createMember(name)
        }

    override suspend fun renameMember(memberId: Int, newName: String) =
        withContext(ioDispatcher) {
            val member = memberLocal.getMemberById(memberId) ?: return@withContext
            memberLocal.updateMember(member.copy(memberName = newName))
        }

    override suspend fun deleteMember(member: Member) =
        withContext(ioDispatcher) {
            memberLocal.deleteMember(member)
        }

    override suspend fun deleteById(memberId: Int) {
        withContext(ioDispatcher){
            memberLocal.deleteById(memberId)
        }
    }
}