package com.chaboi.breakaway.features.scores.data.remote.response.boxscore

data class TeamBoxScoreResponse(
    val away: BoxScoreResponse?,
    val home: BoxScoreResponse?
)