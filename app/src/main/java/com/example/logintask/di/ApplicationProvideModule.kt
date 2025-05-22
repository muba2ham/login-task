package com.example.logintask.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.logintask.repo.remote.RestService
import com.example.logintask.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationProvideModule {

    @Provides
    @Singleton
    fun getRestService(): RestService? = Retrofit.Builder()
        .baseUrl(Constants.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create<RestService>(RestService::class.java)

    @Provides
    @Singleton
    fun getMasterKey(): String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    @Provides
    @Singleton
    fun getEncryptedSharedPreferences(
        @ApplicationContext appContext: Context,
        masterKey: String) : SharedPreferences = EncryptedSharedPreferences.create(
        Constants.SECRET_SHARED_PREFS,
        masterKey,
        appContext,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

}