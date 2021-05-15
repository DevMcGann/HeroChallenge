package com.intermedia.challenge

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.intermedia.challenge.di.businessModule
import com.intermedia.challenge.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MarvelApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
        startKoin {
            androidLogger()
            androidContext(this@MarvelApplication)
            modules(listOf(networkModule, businessModule))
        }
    }
}