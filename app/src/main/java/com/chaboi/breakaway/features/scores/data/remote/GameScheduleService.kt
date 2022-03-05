package com.chaboi.breakaway.features.scores.data.remote

import com.chaboi.breakaway.features.scores.data.remote.response.boxscore.GameBoxScoreResponse
import com.chaboi.breakaway.features.scores.data.remote.response.feed.GameFeedResponse
import com.chaboi.breakaway.features.scores.data.remote.response.schedule.ScheduleResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameScheduleService {

    @GET("api/v1/schedule")
    suspend fun getGamesForDay(@Query("date") date: String): ApiResponse<ScheduleResponse>

    @GET("api/v1/game/{gamePk}/boxscore")
    suspend fun getGameBoxScore(@Path("gamePk") gamePk: String): ApiResponse<GameBoxScoreResponse>

    @GET("api/v1/game/{gamePk}/feed/live")
    suspend fun getLiveGameStats(@Path("gamePk") gamePk: String): ApiResponse<GameFeedResponse>
}