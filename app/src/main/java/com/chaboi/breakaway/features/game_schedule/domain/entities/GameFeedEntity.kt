package com.chaboi.breakaway.features.game_schedule.domain.entities

import com.chaboi.breakaway.util.FINAL
import com.chaboi.breakaway.util.IN_PROGRESS
import com.chaboi.breakaway.util.getDateHourMinutesFormat

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

fun GameFeedEntity.getTime(): String {
    return when (this.gameStatus) {
        FINAL -> FINAL
        IN_PROGRESS -> "${this.timeRemaining} | ${this.period}"
        else -> this.gameDate.getDateHourMinutesFormat()
    }
}