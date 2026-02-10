package com.ajidroid.hissab.useCases.memberUseCases

import com.ajidroid.hissab.data.MemberRepository
import jakarta.inject.Inject

class AddMemberUseCase @Inject constructor(
    private val repository: MemberRepository
) {
    suspend operator fun invoke(name: String) {
        repository.addMember(name)
    }
}