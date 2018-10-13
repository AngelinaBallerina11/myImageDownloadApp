package com.angelinaandronova.myimagedownloadapplication.di

import com.angelinaandronova.myimagedownloadapplication.di.modules.AppModule
import com.angelinaandronova.myimagedownloadapplication.di.modules.NetworkModule
import com.angelinaandronova.myimagedownloadapplication.di.modules.ViewModelModule
import com.angelinaandronova.myimagedownloadapplication.ui.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, ViewModelModule::class))
interface AppComponent {

    fun inject(mainFragment: MainFragment)
}