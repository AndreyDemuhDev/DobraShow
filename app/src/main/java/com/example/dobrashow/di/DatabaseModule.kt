package com.example.dobrashow.di

import android.content.Context
import com.example.database.ShowsDatabase
import com.example.database.showsAppDatabase
import com.example.database.dao.ShowsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideShowsDatabase(@ApplicationContext context: Context): ShowsDatabase {
        return showsAppDatabase(applicationContext = context)
    }

    @Provides
    @Singleton
    fun provideShowsDao(showsDatabase: ShowsDatabase): ShowsDao {
        return showsDatabase.showsDao
    }
}