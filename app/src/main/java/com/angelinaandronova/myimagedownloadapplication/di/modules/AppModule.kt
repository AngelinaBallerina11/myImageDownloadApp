package com.angelinaandronova.myimagedownloadapplication.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.angelinaandronova.myimagedownloadapplication.ui.main.MainRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application = application

    @Provides
    internal fun provideContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun getAppPreferences(): SharedPreferences {
        return application.getSharedPreferences(Companion.USER_ACCOUNT_DETAILS, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideMainRepository(retrofit: Retrofit): MainRepository {
        return MainRepository(retrofit)
    }

    companion object {
        private const val USER_ACCOUNT_DETAILS = "user_account_details"
    }
}