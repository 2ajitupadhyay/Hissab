package com.ajidroid.hissab.auth.domain.useCase

import com.ajidroid.hissab.auth.domain.repository.AuthRepository
import jakarta.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String) {
        repository.signInWithGoogle(idToken)
    }
}