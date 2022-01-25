package com.chaboi.breakaway.features.game_schedule.domain.entities

data class GameBoxScoreEntity(
    val gamePk: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeScore: Int,
    val awayScore: Int,
    val awayShots: Int,
    val homeShots: Int
)
