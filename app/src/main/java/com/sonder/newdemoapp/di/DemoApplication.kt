package com.sonder.newdemoapp.di

import android.app.Application
import com.squareup.picasso.Picasso
import com.sonder.newdemoapp.BuildConfig
import com.sonder.newdemoapp.data.RecipeRepo
import com.sonder.newdemoapp.ui.main.MainActivityViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import org.koin.dsl.module

class DemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()


        startKoin {
            if (BuildConfig.DEBUG) androidLogger() else EmptyLogger()
            androidContext(this@DemoApplication)
            modules(demoApp)
        }
    }
}

val dataModule = module {
    single { ApiProvider().createRecipeAPI() }
}

val uiModule = module {
    single { RecipeRepo(get()) }
    viewModel { MainActivityViewModel(get()) }
    single { Picasso.get() }
}

val rxModule = module {
    // provided components
    single { ApplicationSchedulerProvider() as SchedulerProvider }
}

val demoApp = listOf(dataModule, uiModule, rxModule)