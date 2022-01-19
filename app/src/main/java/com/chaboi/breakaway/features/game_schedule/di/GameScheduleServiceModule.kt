package com.chaboi.breakaway.features.game_schedule.di

import com.chaboi.breakaway.features.game_schedule.data.remote.GameScheduleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GameScheduleServiceModule {

    @Provides
    @Singleton
    fun provideGameScheduleService(retrofit: Retrofit) = retrofit.create(GameScheduleService::class.java)

}