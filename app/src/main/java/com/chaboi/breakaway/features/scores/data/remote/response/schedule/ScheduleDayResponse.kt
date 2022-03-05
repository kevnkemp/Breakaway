package com.chaboi.breakaway.features.scores.data.remote.response.schedule

data class ScheduleDayResponse(
    val date: String,
    val games: List<GameResponse>
)
