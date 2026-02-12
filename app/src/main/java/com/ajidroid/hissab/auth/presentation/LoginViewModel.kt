package com.ajidroid.hissab.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajidroid.hissab.auth.domain.AuthException
import com.ajidroid.hissab.auth.domain.useCase.GoogleSignInUseCase
import com.ajidroid.hissab.auth.domain.useCase.SendEmailVerificationUseCase
import com.ajidroid.hissab.auth.domain.useCase.SendPasswordResetUseCase
import com.ajidroid.hissab.auth.domain.useCase.SignInUseCase
import com.ajidroid.hissab.auth.domain.useCase.SignUpUseCase
import com.ajidroid.hissab.dataStore.domain.UserMode
import com.ajidroid.hissab.dataStore.domain.UserModeStore
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val sendPasswordResetUseCase: SendPasswordResetUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val userModeStore: UserModeStore
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    fun onEmailChange(value: String) {
        _state.update { it.copy(email = value) }
    }

    fun onPasswordChange(value: String) {
        _state.update { it.copy(password = value) }
    }

    private fun setLoading() {
        _state.update { it.copy(isLoading = true, error = null, message = null) }
    }

    private fun setError(message: String?) {
        _state.update { it.copy(isLoading = false, error = message) }
    }

    private fun setMessage(message: String) {
        _state.update { it.copy(isLoading = false, message = message) }
    }

    fun clearMessage() {
        _state.update { it.copy(message = null) }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }

    fun login() {
        viewModelScope.launch {
            setLoading()

            try {
                signInUseCase(
                    _state.value.email,
                    _state.value.password
                )

                userModeStore.setUserMode(UserMode.SIGNED_IN)
                userModeStore.markUserDecided()

            } catch (e: AuthException.EmailNotVerified) {
                setError(e.message)

            } catch (e: AuthException) {
                setError(e.message)

            } catch (e: Exception) {
                Log.d("LoginViewModel", "Login Failed with exception: $e")
                setError("Login failed")
            }
        }
    }

    fun signUp() {
        viewModelScope.launch {
            setLoading()

            try {
                signUpUseCase(
                    _state.value.email,
                    _state.value.password
                )

//                userModeStore.setUserMode(UserMode.SIGNED_IN)
//                userModeStore.markUserDecided()
//                _navigationEvents.emit(LoginNavigationEvent.NavigateToHome)

            } catch (e: AuthException.VerificationEmailSent) {
                setMessage(e.message ?: "Verification email sent, please check your mail")

            } catch (e: AuthException) {
                setError(e.message)

            } catch (e: Exception) {
                setError("Sign up failed")
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            setLoading()

            try {
                googleSignInUseCase(idToken)

                userModeStore.setUserMode(UserMode.SIGNED_IN)
                userModeStore.markUserDecided()

            } catch (e: AuthException) {
                setError(e.message)

            } catch (e: Exception) {
                setError("Google sign-in failed")
            }
        }
    }

    fun resetPassword() {
        if (state.value.email.isBlank()) {
            setError("Please enter your email")
            return
        }

        viewModelScope.launch {
            setLoading()

            try {
                sendPasswordResetUseCase(state.value.email)
                setMessage("Password reset email sent")

            } catch (e: AuthException) {
                setError(e.message)

            } catch (e: Exception) {
                setError("Failed to send reset email")
            }
        }
    }

    fun resendVerificationEmail() {
        viewModelScope.launch {
            setLoading()

            try {
                sendEmailVerificationUseCase()
                setMessage("Verification email sent")

            } catch (e: AuthException) {
                setError(e.message)

            } catch (e: Exception) {
                setError("Failed to send verification email")
            }
        }
    }

    fun continueAsGuest() {
        viewModelScope.launch {
            userModeStore.setUserMode(UserMode.GUEST)
            userModeStore.markUserDecided()
//            _navigationEvents.emit(LoginNavigationEvent.NavigateToHome)
        }
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null
)

sealed class AuthState {
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(
        val userId: String,
        val email: String?
    ) : AuthState()
}

//enum class UserMode {
//    GUEST,
//    SIGNED_IN
//}