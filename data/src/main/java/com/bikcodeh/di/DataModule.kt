package com.bikcodeh.di

import com.bikcodeh.mappers.HobbiesDataMapper
import com.bikcodeh.mappers.PostsDataMapper
import com.bikcodeh.mappers.UsersDataMapper
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
    fun provideDataMapper(): UsersDataMapper {
        return UsersDataMapper()
    }

    @Provides
    @Singleton
    fun providePostsMapper(): PostsDataMapper {
        return PostsDataMapper()
    }

    @Provides
    @Singleton
    fun provideHobbiesMapper(): HobbiesDataMapper {
        return HobbiesDataMapper()
    }
}