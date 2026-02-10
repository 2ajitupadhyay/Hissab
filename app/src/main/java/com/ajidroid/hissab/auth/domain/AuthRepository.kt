package com.ajidroid.hissab.auth.domain

import com.ajidroid.hissab.auth.presentation.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun observeAuthState(): Flow<AuthState>

    suspend fun signIn(email: String, password: String)

    suspend fun signUp(email: String, password: String)

    suspend fun signOut()
}