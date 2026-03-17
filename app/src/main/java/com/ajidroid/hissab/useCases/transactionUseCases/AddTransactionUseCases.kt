package com.ajidroid.hissab.useCases.transactionUseCases

import com.ajidroid.hissab.data.repository.TransactionsRepository
import jakarta.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val repository: TransactionsRepository
) {
    suspend operator fun invoke(
        memberId: Int,
        amount: Int,
        toGive: Boolean,
        description: String?
    ) {
//        repository.addTransaction(
//            memberId,
//            amount,
//            toGive,
//            description
//        )
    }
}