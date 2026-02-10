package com.ajidroid.hissab.auth.data.dataSource

import com.ajidroid.hissab.auth.domain.AuthException
import com.ajidroid.hissab.auth.presentation.AuthState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseAuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthDataSource {

    override fun authState(): Flow<AuthState> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser
            trySend(
                if (user == null) {
                    AuthState.Unauthenticated
                } else {
                    AuthState.Authenticated(
                        userId = user.uid,
                        email = user.email
                    )
                }
            )
        }
        trySend(AuthState.Loading)
        firebaseAuth.addAuthStateListener(listener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(listener)
        }
    }

    override suspend fun signIn(email: String, password: String) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()

            if (firebaseAuth.currentUser?.isEmailVerified != true) {
                throw AuthException.EmailNotVerified()
            }

        } catch (t: Throwable) {
            throw FirebaseErrorMapper.map(t)
        }
    }

    override suspend fun signUp(email: String, password: String) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            firebaseAuth.currentUser?.sendEmailVerification()?.await()
            throw AuthException.VerificationEmailSent()

        } catch (t: Throwable) {
            throw FirebaseErrorMapper.map(t)
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override fun currentUserId(): String? =
        firebaseAuth.currentUser?.uid

    override suspend fun sendEmailVerification() {
        firebaseAuth.currentUser
            ?.sendEmailVerification()
            ?.await()
    }

    override suspend fun sendPasswordReset(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    override suspend fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).await()
    }

    override fun isEmailVerified(): Boolean =
        firebaseAuth.currentUser?.isEmailVerified == true

    override fun currentUserExists(): Boolean =
        firebaseAuth.currentUser != null
}