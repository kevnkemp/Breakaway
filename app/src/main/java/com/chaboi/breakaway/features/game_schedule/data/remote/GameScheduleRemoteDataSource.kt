package com.chaboi.breakaway.features.game_schedule.data.remote

import com.chaboi.breakaway.features.game_schedule.data.remote.response.ScheduleResponse
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class GameScheduleRemoteDataSource @Inject constructor(
    private val gameScheduleService: GameScheduleService
) : GameScheduleRemoteDataSourceContract {

    override suspend fun getGamesForDay(): ApiResponse<ScheduleResponse> = gameScheduleService.getGamesForDay()
}