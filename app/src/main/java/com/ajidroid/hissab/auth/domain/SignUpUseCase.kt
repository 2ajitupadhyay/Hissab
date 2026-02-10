package com.ajidroid.hissab.auth.domain

import jakarta.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        repo.signUp(email, password)
    }
}