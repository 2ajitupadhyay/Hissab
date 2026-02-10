package com.ajidroid.hissab.useCases.transactionUseCases

import com.ajidroid.hissab.data.MemberRepository
import jakarta.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val repository: MemberRepository
) {
    suspend operator fun invoke(
        memberId: Int,
        amount: Int,
        toGive: Boolean,
        description: String?
    ) {
        repository.addTransaction(
            memberId,
            amount,
            toGive,
            description
        )
    }
}