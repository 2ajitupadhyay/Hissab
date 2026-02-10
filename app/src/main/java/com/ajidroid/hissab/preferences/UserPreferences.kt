package com.ajidroid.hissab.preferences

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    val isGuest: Flow<Boolean>
    suspend fun setGuestMode(isGuest: Boolean)
}