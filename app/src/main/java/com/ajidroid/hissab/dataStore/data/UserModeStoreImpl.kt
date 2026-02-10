package com.ajidroid.hissab.dataStore.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ajidroid.hissab.dataStore.domain.UserMode
import com.ajidroid.hissab.dataStore.domain.UserModeStore
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserModeStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserModeStore {

    private val KEY_USER_MODE = stringPreferencesKey("user_mode")
    private val KEY_HAS_DECIDED = booleanPreferencesKey("has_user_decided")

    override val userModeFlow: Flow<UserMode> =
        dataStore.data.map { prefs ->
            when (prefs[KEY_USER_MODE]) {
                UserMode.SIGNED_IN.name -> UserMode.SIGNED_IN
                else -> UserMode.GUEST
            }
        }

    override val hasUserDecidedFlow: Flow<Boolean> =
        dataStore.data.map { prefs ->
            prefs[KEY_HAS_DECIDED] ?: false
        }

    override suspend fun setUserMode(mode: UserMode) {
        dataStore.edit { prefs ->
            prefs[KEY_USER_MODE] = mode.name
        }
    }

    override suspend fun markUserDecided() {
        dataStore.edit { prefs ->
            prefs[KEY_HAS_DECIDED] = true
        }
    }
}