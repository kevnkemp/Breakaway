package com.chaboi.breakaway.features.scores.domain.entities

data class GameScheduleEntity(
    val gamePk: String,
    val awayTeam: String,
    val homeTeam: String,
    val timeOfGame: String,
    val awayScore: Int?,
    val homeScore: Int?
)
