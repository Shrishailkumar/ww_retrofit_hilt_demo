package com.zensar.retrofit_hilt_wwdemo

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/**
All apps that use Hilt must contain an Application class that is annotated with @HiltAndroidApp.
@HiltAndroidApp triggers Hilt's code generation,
including a base class for your application that serves as the application-level dependency container.
 */
@HiltAndroidApp
class WWRetrofitCachingHiltDemoApp : Application() {
    companion object {
        var appContext :Context?=null
    }

    override fun onCreate() {
        super.onCreate()
     appContext = this.applicationContext
    }
    override fun getApplicationContext(): Context {
        return super.getApplicationContext()
    }

}