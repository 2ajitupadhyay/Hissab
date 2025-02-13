package com.ajidroid.hissab.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajidroid.hissab.data.Member
import com.ajidroid.hissab.data.MembersRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeScreenViewModel(val membersRepository: MembersRepository) : ViewModel() {

    val homeUiState : StateFlow<HomeUiState> =
        membersRepository.getAllMembersStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val memberList: List<Member> = listOf())