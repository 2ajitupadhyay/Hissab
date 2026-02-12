package com.ajidroid.hissab.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajidroid.hissab.auth.domain.useCase.GetAuthStateUseCase
import com.ajidroid.hissab.auth.presentation.AuthState
import com.ajidroid.hissab.dataStore.domain.UserMode
import com.ajidroid.hissab.dataStore.domain.UserModeStore
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class StartViewModel @Inject constructor(
    observeAuthState: GetAuthStateUseCase,
    private val userModeStore: UserModeStore
) : ViewModel() {

    private val _startDestination =
        MutableStateFlow<StartDestination>(StartDestination.Splash)
    val startDestination = _startDestination.asStateFlow()

    init {// By default what is the viewModelScope coroutine Dispatcher mode ??
        viewModelScope.launch(Dispatchers.IO) {//Difference between withContext(Dispatchers.IO) and launch(IO)
            combine(
                observeAuthState(),
                userModeStore.userModeFlow,
                userModeStore.hasUserDecidedFlow
            ) { authState, userMode, hasDecided ->
                decideStartDestination(authState, userMode, hasDecided)
            }
                .distinctUntilChanged()
                .collect { destination ->
                    _startDestination.value = destination
                }
        }
    }

    private suspend fun decideStartDestination(
        authState: AuthState,
        userMode: UserMode,
        hasDecided: Boolean
    ): StartDestination {

        if (!hasDecided) {
            return StartDestination.Login
        }

        return when (authState) {

            AuthState.Loading -> StartDestination.Splash

            is AuthState.Authenticated -> {
                // Auto-fix invalid state
                if (userMode != UserMode.SIGNED_IN) {
                    userModeStore.setUserMode(UserMode.SIGNED_IN)
                }
                StartDestination.Home
            }

            AuthState.Unauthenticated -> {
                when (userMode) {
                    UserMode.GUEST -> StartDestination.Home
                    UserMode.SIGNED_IN -> {
                        // User wanted sign-in but isn't authenticated
                        userModeStore.setUserMode(UserMode.GUEST)
                        StartDestination.Login
                    }
                }
            }
        }
    }
}

sealed class StartDestination {
    object Splash : StartDestination()
    object Login : StartDestination()
    object Home : StartDestination()
}