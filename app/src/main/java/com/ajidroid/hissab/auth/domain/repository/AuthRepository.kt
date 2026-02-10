package com.ajidroid.hissab.auth.domain.repository

import com.ajidroid.hissab.auth.presentation.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun observeAuthState(): Flow<AuthState>

    suspend fun signIn(email: String, password: String)

    suspend fun signUp(email: String, password: String)

    suspend fun signOut()

    fun currentUserId(): String?

    suspend fun sendEmailVerification()

    suspend fun sendPasswordReset(email: String)

    suspend fun signInWithGoogle(idToken: String)

    fun isEmailVerified(): Boolean

    fun currentUserExists(): Boolean
}