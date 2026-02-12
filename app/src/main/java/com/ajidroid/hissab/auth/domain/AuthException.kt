package com.ajidroid.hissab.auth.domain

sealed class AuthException(message: String) : Exception(message) {

    class EmailNotVerified :
        AuthException("Please verify your email before signing in")

    class UserAlreadyExists :
        AuthException("This email is already registered")

    class InvalidCredentials :
        AuthException("Invalid email or password")

    class NetworkError :
        AuthException("Network error. Please try again")

    class VerificationEmailSent :
        AuthException("Verification email sent, please check your mail")

    class Unknown(msg: String?) :
        AuthException(msg ?: "Authentication failed")
}