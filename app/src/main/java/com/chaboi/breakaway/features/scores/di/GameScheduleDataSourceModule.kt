package com.chaboi.breakaway.features.scores.di

import com.chaboi.breakaway.features.scores.data.GameScheduleRepository
import com.chaboi.breakaway.features.scores.data.remote.GameScheduleRemoteDataSource
import com.chaboi.breakaway.features.scores.data.remote.GameScheduleRemoteDataSourceContract
import com.chaboi.breakaway.features.scores.data.remote.GameScheduleService
import com.chaboi.breakaway.features.scores.domain.abstractions.GameScheduleRepositoryContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GameScheduleDataSourceModule {

    @Provides
    fun provideGameScheduleRepository(remoteDataSource: GameScheduleRemoteDataSourceContract): GameScheduleRepositoryContract =
        GameScheduleRepository(remoteDataSource)

    @Provides
    fun provideGameScheduleRemoteDataSource(service: GameScheduleService): GameScheduleRemoteDataSourceContract =
        GameScheduleRemoteDataSource(service)
}