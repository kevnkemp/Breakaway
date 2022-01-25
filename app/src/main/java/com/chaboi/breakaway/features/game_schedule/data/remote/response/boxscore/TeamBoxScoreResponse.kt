package com.chaboi.breakaway.features.game_schedule.data.remote.response.boxscore

data class TeamBoxScoreResponse(
    val away: BoxScoreResponse?,
    val home: BoxScoreResponse?
)