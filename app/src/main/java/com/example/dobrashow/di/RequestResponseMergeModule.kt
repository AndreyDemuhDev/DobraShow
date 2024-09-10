package com.example.dobrashow.di

import com.example.shows_data.RequestResponseMergeStrategy
import com.example.shows_data.model.Shows
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RequestResponseMergeModule {

    @Provides
    @Singleton
    fun providesRequestResponseMergeStrategy(): RequestResponseMergeStrategy<List<Shows>> {
        return RequestResponseMergeStrategy()
    }
}