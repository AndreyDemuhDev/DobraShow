package com.example.dobrashow.di

import com.example.shows_data.MergeStrategy
import com.example.shows_data.model.Shows
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//class MergeStrategyModule {
//
//    @Provides
//    @Singleton
//    fun providesMergeStrategy(): MergeStrategy<List<Shows>> {
//        return MergeStrategy()
//    }
//}