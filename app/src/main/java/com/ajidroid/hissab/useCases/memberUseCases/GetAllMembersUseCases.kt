package com.ajidroid.hissab.useCases.memberUseCases

import com.ajidroid.hissab.data.Member
import com.ajidroid.hissab.data.MemberRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAllMembersUseCase @Inject constructor(
    private val repository: MemberRepository
) {
    operator fun invoke(): Flow<List<Member>> =
        repository.getAllMembers()
}