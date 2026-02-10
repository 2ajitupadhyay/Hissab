package com.ajidroid.hissab.auth.data

import com.ajidroid.hissab.auth.presentation.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRemoteDataSource {
    fun authState(): Flow<AuthState>
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
}