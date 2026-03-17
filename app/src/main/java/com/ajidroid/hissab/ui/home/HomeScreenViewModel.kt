package com.ajidroid.hissab.ui.home

import androidx.compose.runtime.mutableStateSetOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajidroid.hissab.data.Member
import com.ajidroid.hissab.data.MemberDetailsWithTotal
import com.ajidroid.hissab.data.TransactionType
import com.ajidroid.hissab.data.repository.MemberRepository
import com.ajidroid.hissab.data.repository.TransactionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val transactionRepository: TransactionsRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> =
        transactionRepository
            .observeMemberDetailsWithTotal()
            .map { members ->

                HomeUiState(
                    isLoading = false,
                    members = members
                )
            }
            .catch {
                emit(
                    HomeUiState(
                        isLoading = false,
                        error = "Failed to load members"
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeUiState(isLoading = true)
            )

    private val _selectedMembers = mutableStateSetOf<Int>()
    val selectedMembers: Set<Int> get() = _selectedMembers

    fun toggleMember(memberId: Int) {
        if (!_selectedMembers.add(memberId)) {
            _selectedMembers.remove(memberId)
        }
    }

    fun selectAll(ids: List<Int>) {
        _selectedMembers.clear()
        _selectedMembers.addAll(ids)
    }

    fun clearSelection() {
        _selectedMembers.clear()
    }

    private val _events = MutableSharedFlow<SplitEvent>()
    val events = _events.asSharedFlow()

    fun addMember(name: String) {
        if (name.isBlank()) return

        viewModelScope.launch {
            memberRepository.addMember(name.trim())
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

    fun deleteMemberById(memberId: Int){
        viewModelScope.launch {
            memberRepository.deleteById(memberId)
        }
    }

    fun addTransaction(
        memberId: Int,
        amount: Int,
        toGive: Boolean,
        description: String?
    ) {
        viewModelScope.launch {
            transactionRepository.addTransaction(
                memberId = memberId,
                amount = amount,
                type = if (toGive) TransactionType.LEND else TransactionType.BORROW,
                description = description
            )
        }
    }

    fun splitWise(
        amount: Double,
        description: String?
    ) {
        val memberIds = _selectedMembers.toList()

        if (memberIds.isEmpty() || amount <= 0) return

        viewModelScope.launch {
            runCatching {
                transactionRepository.splitTransaction(
                    memberIds = memberIds,
                    totalAmount = amount.roundToInt(),
                    description = description?.takeIf { it.isNotBlank() }
                )
            }.onSuccess {
                clearSelection()
                _events.emit(SplitEvent.Success)
            }.onFailure {
                _events.emit(
                    SplitEvent.Error("SplitWise failed")
                )
            }
        }
    }
}

//@HiltViewModel
//class HomeScreenViewModel @Inject constructor(
//    private val repository: MemberLocalDataSourceImpl
//) : ViewModel() {
//
//    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
//    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
//    val selectedMemberIds = mutableStateSetOf<Int>()
//
//    init {
//        getAllMembers()
//    }
//
//    // How do i make this method on IO thread so that it does not block the main thread and cause frame skipped
//    // Also doing [init] is a good approach or should there be another way which causes less or memory usage
//    private fun getAllMembers() {
//        repository.getAllMembers()
//            .onEach { members ->
//                _uiState.value = HomeUiState(
//                    isLoading = false,
//                    members = members
//                )
//            }
//            .catch { throwable ->
//                Log.d("HomeScreenViewModel", "Error fetching the list of Members: ${throwable.message}")
//                _uiState.value = HomeUiState(
//                    isLoading = false,
//                    error = "Failed to load members"
//                )
//                // Log throwable.message for debugging
//            }
//            .launchIn(viewModelScope)
//    }
//
//    fun addMember(memberName: String) {
//        if (memberName.isBlank()) return
//
//        viewModelScope.launch {
//            repository.addMember(memberName.trim())
//        }
//    }
//
//    fun renameMemberName(newName: String, memberId: Int){
//        viewModelScope.launch {
//            repository.renameMember(
//                memberId = memberId,
//                newName = newName
//            )
//        }
//    }
//
//    fun deleteMember(member: Member){
//        viewModelScope.launch {
//            repository.deleteMember(
//                member = member
//            )
//        }
//    }
//
//    fun addTransaction(
//        memberId: Int,
//        amount: Int,
//        toGive: Boolean,
//        description: String? = ""
//    ){
//        viewModelScope.launch {
//            repository.addTransaction(
//                memberId = memberId,
//                amount = amount,
//                toGive = toGive,
//                description = description
//            )
//        }
//    }
//
//    fun toggleMember(id: Int) {
//        if (!selectedMemberIds.add(id)) {
//            selectedMemberIds.remove(id)
//        }
//    }
//
//    fun selectAll(ids: List<Int>) {
//        selectedMemberIds.clear()
//        selectedMemberIds.addAll(ids)
//    }
//
//    fun clearSelection() {
//        selectedMemberIds.clear()
//    }
//
//    private val _events = MutableSharedFlow<SplitEvent>()
//    val events = _events.asSharedFlow()
//
//    fun splitWise(
//        amount: Double,
//        description: String?
//    ) {
//        // Take an immutable snapshot immediately
//        val memberIds = selectedMemberIds.toList()
//        if (memberIds.isEmpty() || amount <= 0) return
//
//        viewModelScope.launch {
//            runCatching {
//
//                val totalAmount = amount.roundToInt()
//                val memberCount = memberIds.size
//
//                val baseAmount = totalAmount / memberCount
//                val remainder = totalAmount % memberCount
//                val timestamp = System.currentTimeMillis()
//
//                val normalizedDescription =
//                    description?.takeIf { it.isNotBlank() }
//
//                val transactions = memberIds.mapIndexed { index, memberId ->
//                    Transactions(
//                        memberId = memberId,
//                        amount = baseAmount + if (index == 0) remainder else 0,
//                        toGive = false,
//                        description = normalizedDescription,//Try later to make the  description start with the name of the selected members for the split
//                        time = timestamp
//                    )
//                }
//
//                repository.splitWise(transactions)
//            }
//                .onSuccess {
//                    clearSelection()
//                    // emit success event if you want (recommended)
//                }
//                .onFailure { throwable ->
//                    // emit error event / log
//                }
//        }
//    }
//}

data class HomeUiState(
    val isLoading: Boolean = false,
    val members: List<MemberDetailsWithTotal> = emptyList(),
    val error: String? = null
)

enum class MemberAction {
    Rename,
    ClearTab,
    Delete
}

sealed interface SplitEvent {
    object Success : SplitEvent
    data class Error(val message: String) : SplitEvent
}