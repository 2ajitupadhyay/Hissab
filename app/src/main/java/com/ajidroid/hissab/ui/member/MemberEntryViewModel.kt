package com.ajidroid.hissab.ui.member

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ajidroid.hissab.data.Member
import com.ajidroid.hissab.data.MembersRepository
import com.ajidroid.hissab.data.Transaction

//class MemberEntryViewModel(private val membersRepository: MembersRepository) : ViewModel() {
//
//    var memberUiState by mutableStateOf(MemberUiState())
//        private set
//
//    fun updateUiState(memberDetails : MemberDetails){
//        memberUiState = MemberUiState(memberDetails =  memberDetails, isEntryValid = validateInput(memberDetails))
//    }
//    suspend fun saveMember() {
//        if (validateInput()){
//            membersRepository.insertMember(memberUiState.memberDetails.toMember())
//        }
//    }
//    private fun validateInput(uiState: MemberDetails = memberUiState.memberDetails) : Boolean {
//        return with(uiState) {
//            memberName.isNotBlank()
//        }
//    }
//}
//
//data class MemberUiState(
//    val memberDetails: MemberDetails = MemberDetails(),
//    val isEntryValid: Boolean = false
//)
//fun MemberDetails.toMember(): Member = Member(
//    id = id,
//    toGive = toGive,
//    memberName = memberName,
//    totalAmount = totalAmount,
//    transactionList = transactionList
//)
//
//fun Member.toMemberUiState(isEntryValid: Boolean = false): MemberUiState = MemberUiState(
//    memberDetails = this.toMemberDetails(),
//    isEntryValid = isEntryValid
//)
//
//data class MemberDetails(
//    val id : Int = 0,
//    var toGive : Boolean = false,
//    val memberName : String = "",
//    var totalAmount : Int = 0,
//    var transactionList: List<Transaction> = mutableListOf()
//)