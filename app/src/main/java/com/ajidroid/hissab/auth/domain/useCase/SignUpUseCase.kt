package com.ajidroid.hissab.auth.domain.useCase

import com.ajidroid.hissab.auth.domain.repository.AuthRepository
import jakarta.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        repository.signUp(email, password)
    }
}