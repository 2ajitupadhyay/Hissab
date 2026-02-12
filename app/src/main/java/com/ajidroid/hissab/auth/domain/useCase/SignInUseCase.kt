package com.ajidroid.hissab.auth.domain.useCase

import com.ajidroid.hissab.auth.domain.repository.AuthRepository
import jakarta.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        repository.signIn(email, password)
    }
}