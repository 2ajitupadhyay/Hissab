package com.ajidroid.hissab.dataStore.domain

import kotlinx.coroutines.flow.Flow

interface UserModeStore {
    val userModeFlow: Flow<UserMode>
    val hasUserDecidedFlow: Flow<Boolean>
    suspend fun setUserMode(mode: UserMode)
    suspend fun markUserDecided()
}