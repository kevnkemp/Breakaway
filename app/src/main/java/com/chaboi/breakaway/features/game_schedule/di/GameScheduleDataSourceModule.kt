package com.chaboi.breakaway.features.game_schedule.di

import com.chaboi.breakaway.features.game_schedule.data.GameScheduleRepository
import com.chaboi.breakaway.features.game_schedule.data.remote.GameScheduleRemoteDataSource
import com.chaboi.breakaway.features.game_schedule.data.remote.GameScheduleRemoteDataSourceContract
import com.chaboi.breakaway.features.game_schedule.data.remote.GameScheduleService
import com.chaboi.breakaway.features.game_schedule.domain.abstractions.GameScheduleRepositoryContract
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