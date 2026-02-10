package com.ajidroid.hissab.auth.data

import com.ajidroid.hissab.auth.domain.AuthRepository
import com.ajidroid.hissab.auth.presentation.AuthState
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthRemoteDataSource
) : AuthRepository {

    override fun observeAuthState(): Flow<AuthState> =
        remote.authState()

    override suspend fun signIn(email: String, password: String) =
        remote.signIn(email, password)

    override suspend fun signUp(email: String, password: String) =
        remote.signUp(email, password)

    override suspend fun signOut() =
        remote.signOut()
}