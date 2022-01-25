package com.chaboi.breakaway.features.game_schedule.domain.entities

data class GameFeedEntity(
    val gamePk: String,
    val gameDate: String,
    val gameStatus: String,
    val homeTeam: String,
    val homeTeamAbbreviated: String,
    val awayTeam: String,
    val awayTeamAbbreviated: String,
    val homeScore: Int,
    val awayScore: Int,
    val homeShots: Int,
    val awayShots: Int,
    val timeRemaining: String,
    val period: String
)