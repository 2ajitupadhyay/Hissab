package com.ajidroid.hissab.data

import android.content.Context

interface AppContainer {
    val membersRepository: MembersRepository
}

class AppDataContainer(private val context : Context) : AppContainer {
    override val membersRepository: MembersRepository by lazy {
        OfflineMembersRepository(HissabDatabase.getDatabase(context).memberDao())
    }
}