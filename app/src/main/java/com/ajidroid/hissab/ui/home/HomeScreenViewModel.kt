package com.ajidroid.hissab.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajidroid.hissab.data.Member
import com.ajidroid.hissab.data.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: MemberRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

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