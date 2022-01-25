package com.chaboi.breakaway.features.game_schedule.data.remote.response.schedule

data class GameMatchupResponse(
    val away: GameTeamResponse,
    val home: GameTeamResponse
)