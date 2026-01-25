package com.ajidroid.hissab.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateSetOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajidroid.hissab.data.Member
import com.ajidroid.hissab.data.MemberRepository
import com.ajidroid.hissab.data.Transactions
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: MemberRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    val selectedMemberIds = mutableStateSetOf<Int>()

    init {
        getAllMembers()
    }

    private fun getAllMembers() {
        repository.getAllMembers()
            .onEach { members ->
                _uiState.value = HomeUiState(
                    isLoading = false,
                    members = members
                )
            }
            .catch { throwable ->
                Log.d("HomeScreenViewModel", "Error fetching the list of Members: ${throwable.message}")
                _uiState.value = HomeUiState(
                    isLoading = false,
                    error = "Failed to load members"
                )
                // Log throwable.message for debugging
            }
            .launchIn(viewModelScope)
    }

    fun addMember(memberName: String) {
        if (memberName.isBlank()) return

        viewModelScope.launch {
            repository.addMember(memberName.trim())
        }
    }

    fun renameMemberName(newName: String, memberId: Int){
        viewModelScope.launch {
            repository.renameMember(
                memberId = memberId,
                newName = newName
            )
        }
    }

    fun deleteMember(member: Member){
        viewModelScope.launch {
            repository.deleteMember(
                member = member
            )
        }
    }

    fun addTransaction(
        memberId: Int,
        amount: Int,
        toGive: Boolean,
        description: String? = ""
    ){
        viewModelScope.launch {
            repository.addTransaction(
                memberId = memberId,
                amount = amount,
                toGive = toGive,
                description = description
            )
        }
    }

    fun toggleMember(id: Int) {
        if (!selectedMemberIds.add(id)) {
            selectedMemberIds.remove(id)
        }
    }

    fun selectAll(ids: List<Int>) {
        selectedMemberIds.clear()
        selectedMemberIds.addAll(ids)
    }

    fun clearSelection() {
        selectedMemberIds.clear()
    }

    private val _events = MutableSharedFlow<SplitEvent>()
    val events = _events.asSharedFlow()

    fun splitWise(
        amount: Double,
        description: String?
    ) {
        // Take an immutable snapshot immediately
        val memberIds = selectedMemberIds.toList()
        if (memberIds.isEmpty() || amount <= 0) return

        viewModelScope.launch {
            runCatching {

                val totalAmount = amount.roundToInt()
                val memberCount = memberIds.size

                val baseAmount = totalAmount / memberCount
                val remainder = totalAmount % memberCount
                val timestamp = System.currentTimeMillis()

                val normalizedDescription =
                    description?.takeIf { it.isNotBlank() }

                val transactions = memberIds.mapIndexed { index, memberId ->
                    Transactions(
                        memberId = memberId,
                        amount = baseAmount + if (index == 0) remainder else 0,
                        toGive = false,
                        description = normalizedDescription,//Try later to make the  description start with the name of the selected members for the split
                        time = timestamp
                    )
                }

                repository.splitWise(transactions)
            }
                .onSuccess {
                    clearSelection()
                    // emit success event if you want (recommended)
                }
                .onFailure { throwable ->
                    // emit error event / log
                }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val members: List<Member> = emptyList(),
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