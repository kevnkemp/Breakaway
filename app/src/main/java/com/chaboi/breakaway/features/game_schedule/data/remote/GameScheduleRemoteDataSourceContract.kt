package com.chaboi.breakaway.features.game_schedule.data.remote

import com.chaboi.breakaway.features.game_schedule.data.remote.response.ScheduleResponse
import com.skydoves.sandwich.ApiResponse

interface GameScheduleRemoteDataSourceContract {

    suspend fun getGamesForDay(): ApiResponse<ScheduleResponse>
}