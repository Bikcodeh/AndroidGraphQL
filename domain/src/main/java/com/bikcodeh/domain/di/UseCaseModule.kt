package com.bikcodeh.domain.di

import com.bikcodeh.domain.repository.MainRepository
import com.bikcodeh.domain.usecase.GetUsersUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetUsersUseCase(mainRepository: MainRepository): GetUsersUC = GetUsersUC(
        mainRepository
    )
}