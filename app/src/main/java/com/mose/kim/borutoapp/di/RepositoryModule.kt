package com.mose.kim.borutoapp.di

import android.content.Context
import com.mose.kim.borutoapp.data.repository.DataStoreOperationsImpl
import com.mose.kim.borutoapp.data.repository.Repository
import com.mose.kim.borutoapp.domain.repository.DataStoreOperations
import com.mose.kim.borutoapp.domain.use_case.UseCase
import com.mose.kim.borutoapp.domain.use_case.read_onboarding.ReadOnBoardingUseCase
import com.mose.kim.borutoapp.domain.use_case.save_onboarding.SaveOnBoardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataStoreOperationsImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: Repository): UseCase {
        return UseCase(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository)
        )
    }
}