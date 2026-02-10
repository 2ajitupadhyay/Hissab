package com.ajidroid.hissab.auth.domain

import com.ajidroid.hissab.auth.presentation.AuthState
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAuthStateUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<AuthState> =
        repository.observeAuthState()
}