@file:OptIn(ExperimentalCoroutinesApi::class)

package com.ajidroid.hissab.ui.member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajidroid.hissab.data.Member
import com.ajidroid.hissab.data.TransactionType
import com.ajidroid.hissab.data.Transactions
import com.ajidroid.hissab.data.repository.MemberRepository
import com.ajidroid.hissab.data.repository.TransactionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MemberDetailUiState(
    val isLoading: Boolean = true,
    val memberName: String = "",
    val transactions: List<Transactions> = emptyList(),
    val isEmpty: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class MemberDetailViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val transactionsRepository: TransactionsRepository
) : ViewModel() {

    private val memberId = MutableStateFlow<Int?>(null)

    val uiState: StateFlow<MemberDetailUiState> =
        memberId
            .filterNotNull()
            .distinctUntilChanged()
            .flatMapLatest { id ->

                combine(
                    memberRepository.observeMemberById(id),
                    transactionsRepository.observeTransactions(id)
                ) { member: Member? , transactions: List<Transactions> ->

                    if (member == null) {
                        MemberDetailUiState(
                            isLoading = false,
                            isEmpty = true
                        )
                    } else {
                        MemberDetailUiState(
                            isLoading = false,
                            memberName = member.memberName,
                            transactions = transactions,
                            isEmpty = transactions.isEmpty()
                        )
                    }
                }
                    .onStart { emit(MemberDetailUiState(isLoading = true)) }
                    .catch { e ->
                        emit(
                            MemberDetailUiState(
                                isLoading = false,
                                error = e.message ?: "Something went wrong"
                            )
                        )
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MemberDetailUiState(isLoading = true)
            )

    private fun onStart(function: Any) {}

    fun loadMember(selectedMemberId: Int) {
        if (memberId.value != selectedMemberId) {
            memberId.value = selectedMemberId
        }
    }

    fun renameMember(memberId: Int, newName: String) {
        viewModelScope.launch {
            memberRepository.renameMember(memberId, newName)
        }
    }

    fun deleteMember(member: Member) {
        viewModelScope.launch {
            memberRepository.deleteMember(member)
        }
    }

    fun addTransaction(
        memberId: Int,
        amount: Int,
        type: TransactionType,
        description: String?
    ) {
        viewModelScope.launch {
            transactionsRepository.addTransaction(
                memberId = memberId,
                amount = amount,
                type = type,
                description = description
            )
        }
    }

//    fun splitTransaction(
//        memberIds: List<Int>,
//        totalAmount: Int,
//        description: String?
//    ) {
//        viewModelScope.launch {
//            transactionsRepository.splitTransaction(
//                memberIds = memberIds,
//                totalAmount = totalAmount,
//                description = description
//            )
//        }
//    }
}

//@HiltViewModel
//class MemberDetailViewModel @Inject constructor(
//    private val repository: MemberLocalDataSourceImpl
//) : ViewModel() {
//
//    private val _memberId = MutableStateFlow<Int?>(null)
//
//    val uiState: StateFlow<MemberDetailUiState> =
//        _memberId
//            .filterNotNull()
//            .flatMapLatest { memberId ->
//                repository.getMemberWithTransactions(memberId) // member + transactions
//            }
//            .map { result ->
//                when {
//                    result == null -> {
//                        MemberDetailUiState(
//                            isLoading = false,
//                            isEmpty = true
//                        )
//                    }
//
//                    else -> {
//                        MemberDetailUiState(
//                            isLoading = false,
//                            memberName = result.member.memberName,
//                            transactions = result.transactions,
//                            isEmpty = result.transactions.isEmpty()
//                        )
//                    }
//                }
//            }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000),
//                initialValue = MemberDetailUiState(isLoading = true)
//            )
//
//    fun loadMember(memberId: Int) {
//        if (_memberId.value != memberId) {
//            _memberId.value = memberId
//        }
//    }
//}

//class MemberDetailViewModel(
//    savedStateHandle: SavedStateHandle,
//    private val membersRepository: MembersRepository
//) : ViewModel() {
//    private val memberId : Int = checkNotNull(savedStateHandle[0.toString()])
//
//    val uiState : StateFlow<MemberDetailsUiState> =
//        membersRepository.getMemberStream(memberId)
//            .filterNotNull()
//            .map { MemberDetailsUiState(noTransactions = it.transactionList.size == 0, memberDetails = it.toMemberDetails())
//            }.stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = MemberDetailsUiState()
//            )
//    /**
//     * Deletes the item from the [ItemsRepository]'s data source.
//     */
//
//    /*
//     * Moving it to add transaction screen
//     */
//
//    companion object{
//        private const val TIMEOUT_MILLIS = 5_000L
//    }
//}
//
//data class MemberDetailsUiState(
//    val noTransactions: Boolean = true,
//    val memberDetails: MemberDetails = MemberDetails()
//)
//
//fun Member.toMemberDetails() : MemberDetails = MemberDetails(
//    id = id,
//    toGive = toGive,
//    memberName = memberName,
//    totalAmount = totalAmount,
//    transactionList = transactionList
//)