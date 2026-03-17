package com.ajidroid.hissab.useCases.transactionUseCases

import com.ajidroid.hissab.data.Member
import com.ajidroid.hissab.data.Transactions
import com.ajidroid.hissab.data.repository.MemberRepository
import com.ajidroid.hissab.data.repository.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ObserveMemberDetailUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val transactionsRepository: TransactionsRepository
) {

    operator fun invoke(memberId: Int): Flow<MemberDetailWithTransaction?> {

        return combine(
            memberRepository.observeMemberById(memberId),
            transactionsRepository.observeTransactions(memberId)
        ) { member: Member?, transactions: List<Transactions> ->

            member?.let {
                MemberDetailWithTransaction(
                    member = it,
                    transactions = transactions
                )
            }
        }
    }
}

data class MemberDetailWithTransaction(
    val member: Member,
    val transactions: List<Transactions>
)