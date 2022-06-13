package com.bikcodeh.di

import com.bikcodeh.data.DataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDataMapper(): DataMapper {
        return DataMapper()
    }
}