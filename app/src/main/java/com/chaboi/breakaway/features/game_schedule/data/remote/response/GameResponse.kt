package com.chaboi.breakaway.features.game_schedule.data.remote.response

data class GameResponse(
    val gamePk: String,
    val gameDate: String,
    val teams: GameMatchupResponse,

)
