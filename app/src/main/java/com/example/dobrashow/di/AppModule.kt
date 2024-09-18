package com.example.dobrashow.di

import android.content.Context
import com.example.core.AppCustomDispatchers
import com.example.database.ShowsDatabase
import com.example.database.showsAppDatabase
import com.example.network.KtorClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // провайдим сетевой клиент
    @Provides
    @Singleton
    fun providesKtorClient(): KtorClient {
        return KtorClient()
    }

    // провайдим базу данных
    @Provides
    @Singleton
    fun provideShowsDatabase(@ApplicationContext context: Context): ShowsDatabase {
        return showsAppDatabase(applicationContext = context)
    }

    // провайдим диспатчеры приложения
    @Provides
    @Singleton
    fun provideAppCustomDispatchers(): AppCustomDispatchers = AppCustomDispatchers()
}