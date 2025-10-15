package com.hurreytech.stopwatch.di

import com.hurreytech.stopwatch.domain.repository.StopwatchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStopwatchRepository(): StopwatchRepository {
        return StopwatchRepository()
    }
}
