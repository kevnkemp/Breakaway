package com.chaboi.breakaway.features.game_schedule.data.remote

import com.chaboi.breakaway.features.game_schedule.data.remote.response.ScheduleResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface GameScheduleService {

    @GET("api/v1/schedule")
    suspend fun getGamesForDay(): ApiResponse<ScheduleResponse>
}