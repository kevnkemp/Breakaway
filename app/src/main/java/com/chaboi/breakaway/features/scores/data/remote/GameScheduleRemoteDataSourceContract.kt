package com.chaboi.breakaway.features.scores.data.remote

import com.chaboi.breakaway.features.scores.data.remote.response.boxscore.GameBoxScoreResponse
import com.chaboi.breakaway.features.scores.data.remote.response.feed.GameFeedResponse
import com.chaboi.breakaway.features.scores.data.remote.response.schedule.ScheduleResponse
import com.skydoves.sandwich.ApiResponse

interface GameScheduleRemoteDataSourceContract {

    suspend fun getGamesForDay(date: String): ApiResponse<ScheduleResponse>

    suspend fun getGameBoxScore(gamePk: String): ApiResponse<GameBoxScoreResponse>

    suspend fun getLiveGameStats(gamePk: String): ApiResponse<GameFeedResponse>
}