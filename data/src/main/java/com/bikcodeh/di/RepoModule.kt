package com.bikcodeh.di

import com.bikcodeh.data.MainRepositoryImpl
import com.bikcodeh.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindMainRepository(
        repo: MainRepositoryImpl
    ): MainRepository
}