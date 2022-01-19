package com.chaboi.breakaway.features.game_schedule.data.remote.response

data class GameMatchupResponse(
    val away: GameTeamResponse,
    val home: GameTeamResponse
)