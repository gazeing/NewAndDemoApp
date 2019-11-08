package com.willow.newreactdemoapp.di

import android.app.Application
import com.willow.newreactdemoapp.BuildConfig
import com.willow.newreactdemoapp.data.RecipeAPI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import org.koin.dsl.module

class DemoApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        val dataModule = module {
            single { ApiProvider() }
        }

        startKoin {
            if (BuildConfig.DEBUG) androidLogger() else EmptyLogger()
            androidContext(this@DemoApplication)
            modules(listOf(dataModule))
        }
    }
}