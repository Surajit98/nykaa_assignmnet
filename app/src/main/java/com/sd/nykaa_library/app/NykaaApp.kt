package com.sd.nykaa_library.app

import android.app.Application
import com.sd.nykaa_library.di.dataBaseModule
import com.sd.nykaa_library.di.repositoryModule
import com.sd.nykaa_library.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NykaaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@NykaaApp)
            modules(listOf(viewModelModule, dataBaseModule, repositoryModule))
        }
    }
}