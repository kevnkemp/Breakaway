package com.chaboi.breakaway.features.scores.data.remote.response.schedule

data class GameMatchupResponse(
    val away: GameTeamResponse,
    val home: GameTeamResponse
)