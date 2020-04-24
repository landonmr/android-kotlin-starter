package com.landon.starterproject

import android.app.Application
import com.landon.starterproject.data.CharacterRepository
import com.landon.starterproject.networking.Network
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    private var listOfModules = module {
        single { Network() }
        single { CharacterRepository() }
    }


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOfModules)
        }
    }
}