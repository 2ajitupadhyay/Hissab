package com.ajidroid.hissab.auth.data.repository

import com.ajidroid.hissab.auth.data.dataSource.AuthDataSource
import com.ajidroid.hissab.auth.domain.repository.AuthRepository
import com.ajidroid.hissab.auth.presentation.AuthState
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthDataSource
) : AuthRepository {

    override fun observeAuthState(): Flow<AuthState> =
        remote.authState()

    override suspend fun signIn(email: String, password: String) =
        remote.signIn(email, password)

    override suspend fun signUp(email: String, password: String) =
        remote.signUp(email, password)

    override suspend fun signOut() =
        remote.signOut()

    override fun currentUserId(): String? =
        remote.currentUserId()

    override suspend fun sendEmailVerification() =
        remote.sendEmailVerification()

    override suspend fun sendPasswordReset(email: String) =
        remote.sendPasswordReset(email)

    override suspend fun signInWithGoogle(idToken: String) =
        remote.signInWithGoogle(idToken)

    override fun isEmailVerified(): Boolean =
        remote.isEmailVerified()

    override fun currentUserExists(): Boolean =
        remote.currentUserExists()
}