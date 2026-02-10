package com.ajidroid.hissab.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajidroid.hissab.auth.domain.GetAuthStateUseCase
import com.ajidroid.hissab.auth.domain.SignInUseCase
import com.ajidroid.hissab.auth.domain.SignOutUseCase
import com.ajidroid.hissab.auth.domain.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    getAuthState: GetAuthStateUseCase,
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    val authState: StateFlow<AuthState> =
        getAuthState()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                AuthState.Loading
            )

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            signInUseCase(email, password)
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            signUpUseCase(email, password)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
        }
    }
}

sealed class AuthState {
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(
        val userId: String,
        val email: String?
    ) : AuthState()
}

enum class UserMode {
    GUEST,
    SIGNED_IN
}