package com.chaboi.breakaway.features.game_schedule.data.remote.response.schedule

data class ScheduleDayResponse(
    val date: String,
    val games: List<GameResponse>
)
