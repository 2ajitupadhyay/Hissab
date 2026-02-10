package com.ajidroid.hissab.auth.domain

import jakarta.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repo: AuthRepository,
    private val prefs: UserPreferences
) {
    suspend operator fun invoke() {
        repo.signOut()
        prefs.setGuestMode(true)
    }
}