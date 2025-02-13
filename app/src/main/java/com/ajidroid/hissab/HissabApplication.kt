package com.ajidroid.hissab

import android.app.Application
import com.ajidroid.hissab.data.AppContainer
import com.ajidroid.hissab.data.AppDataContainer

class HissabApplication:Application() {
    lateinit var container : AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}