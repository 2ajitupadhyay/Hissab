package com.ajidroid.hissab.auth.domain.useCase

import com.ajidroid.hissab.auth.domain.repository.AuthRepository
import jakarta.inject.Inject

class CheckUserSessionUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    fun isUserLoggedIn(): Boolean =
        repository.currentUserExists()

    fun isEmailVerified(): Boolean =
        repository.isEmailVerified()
}