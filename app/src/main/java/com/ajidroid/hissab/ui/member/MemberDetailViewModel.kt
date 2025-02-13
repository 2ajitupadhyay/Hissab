package com.ajidroid.hissab.ui.member

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajidroid.hissab.data.Member
import com.ajidroid.hissab.data.MembersRepository
import com.ajidroid.hissab.data.Transaction
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MemberDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val membersRepository: MembersRepository
) : ViewModel() {
    private val memberId : Int = checkNotNull(savedStateHandle[0.toString()])

    val uiState : StateFlow<MemberDetailsUiState> =
        membersRepository.getMemberStream(memberId)
            .filterNotNull()
            .map { MemberDetailsUiState(noTransactions = it.transactionList.size == 0, memberDetails = it.toMemberDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MemberDetailsUiState()
            )
    /**
     * Deletes the item from the [ItemsRepository]'s data source.
     */

    /*
     * Moving it to add transaction screen
     */

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class MemberDetailsUiState(
    val noTransactions: Boolean = true,
    val memberDetails: MemberDetails = MemberDetails()
)

data class MemberDetails(
    val id : Int = 0,
    var toGive : Boolean = false,
    val memberName : String = "",
    var totalAmount : Int = 0,
    var transactionList: List<Transaction> = mutableListOf()
)
fun Member.toMemberDetails() : MemberDetails = MemberDetails(
    id = id,
    toGive = toGive,
    memberName = memberName,
    totalAmount = totalAmount,
    transactionList = transactionList
)