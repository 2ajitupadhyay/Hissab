package com.ajidroid.hissab.auth.domain.useCase

import com.ajidroid.hissab.auth.domain.repository.AuthRepository
import com.ajidroid.hissab.dataStore.domain.UserMode
import com.ajidroid.hissab.dataStore.domain.UserModeStore
import jakarta.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val prefs: UserModeStore
) {
    suspend operator fun invoke() {
        repository.signOut()
        prefs.setUserMode(UserMode.GUEST)
    }
}