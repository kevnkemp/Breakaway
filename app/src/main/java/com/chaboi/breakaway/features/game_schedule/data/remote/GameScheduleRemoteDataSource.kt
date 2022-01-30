package com.chaboi.breakaway.features.game_schedule.data.remote

import com.chaboi.breakaway.features.game_schedule.data.remote.response.boxscore.GameBoxScoreResponse
import com.chaboi.breakaway.features.game_schedule.data.remote.response.feed.GameFeedResponse
import com.chaboi.breakaway.features.game_schedule.data.remote.response.schedule.ScheduleResponse
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class GameScheduleRemoteDataSource @Inject constructor(
    private val gameScheduleService: GameScheduleService
) : GameScheduleRemoteDataSourceContract {

    override suspend fun getGamesForDay(date: String): ApiResponse<ScheduleResponse> =
        gameScheduleService.getGamesForDay(date)

    override suspend fun getGameBoxScore(gamePk: String): ApiResponse<GameBoxScoreResponse> =
        gameScheduleService.getGameBoxScore(gamePk)

    override suspend fun getLiveGameStats(gamePk: String): ApiResponse<GameFeedResponse> =
        gameScheduleService.getLiveGameStats(gamePk)
}