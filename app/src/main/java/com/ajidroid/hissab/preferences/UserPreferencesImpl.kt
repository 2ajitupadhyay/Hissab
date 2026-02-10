package com.ajidroid.hissab.preferences

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserPreferencesImpl @Inject constructor(
    @ApplicationContext context: Context
) : UserPreferences {

    private val dataStore = context.createDataStore(
        name = "user_prefs"
    )

    private object Keys {
        val IS_GUEST = booleanPreferencesKey("is_guest")
    }

    override val isGuest: Flow<Boolean> =
        dataStore.data.map { prefs ->
            prefs[Keys.IS_GUEST] ?: false
        }

    override suspend fun setGuestMode(isGuest: Boolean) {
        dataStore.edit { prefs ->
            prefs[Keys.IS_GUEST] = isGuest
        }
    }
}