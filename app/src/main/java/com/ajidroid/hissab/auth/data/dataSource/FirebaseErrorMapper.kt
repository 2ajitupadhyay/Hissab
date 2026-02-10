package com.ajidroid.hissab.auth.data.dataSource

import com.ajidroid.hissab.auth.domain.AuthException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

object FirebaseErrorMapper {

    fun map(throwable: Throwable): AuthException =
        when (throwable) {

            is FirebaseAuthUserCollisionException ->
                AuthException.UserAlreadyExists()

            is FirebaseAuthInvalidCredentialsException,
            is FirebaseAuthInvalidUserException ->
                AuthException.InvalidCredentials()

            is FirebaseNetworkException ->
                AuthException.NetworkError()

            is AuthException -> throwable

            else -> AuthException.Unknown(throwable.message)
        }
}