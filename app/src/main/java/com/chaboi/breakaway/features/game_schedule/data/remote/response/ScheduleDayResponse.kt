package com.chaboi.breakaway.features.game_schedule.data.remote.response

data class ScheduleDayResponse(
    val date: String,
    val games: List<GameResponse>
)
