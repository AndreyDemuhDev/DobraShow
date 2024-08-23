package com.example.dobrashow.di

import com.example.database.ShowsDatabase
import com.example.database.dao.ShowsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideShowsDao(showsDatabase: ShowsDatabase): ShowsDao {
        return  showsDatabase.showsDao
    }
}