package com.angelinaandronova.myimagedownloadapplication

import android.app.Application
import com.angelinaandronova.myimagedownloadapplication.di.AppComponent
import com.angelinaandronova.myimagedownloadapplication.di.modules.AppModule
import com.angelinaandronova.myimagedownloadapplication.di.DaggerAppComponent
import com.angelinaandronova.myimagedownloadapplication.di.modules.NetworkModule


class MyApplication : Application() {

    companion object {
        private const val BASE_URL: String = "https://mobility.cleverlance.com"
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        @Suppress("DEPRECATION")
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule(BASE_URL))
            .build()
    }
}