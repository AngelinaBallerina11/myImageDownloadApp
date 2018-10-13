package com.angelinaandronova.myimagedownloadapplication.di.modules

import android.content.SharedPreferences
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import javax.inject.Singleton
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NetworkModule(var mBaseUrl: String) {

    companion object {
        val TOKEN: String = "hashedPassword"
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(sharedPrefs: SharedPreferences): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor { chain ->
            val builder = chain.request().newBuilder()
            sharedPrefs.getString(TOKEN, null)?.let { token -> builder.addHeader("Authorization", token) }
            chain.proceed(builder.build())
            }
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(mBaseUrl)
            .client(okHttpClient)
            .build()
    }
}