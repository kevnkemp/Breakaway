package com.chaboi.breakaway.features.game_schedule.data.remote.response.boxscore

data class TeamSkaterStatsResponse(
    val goals: Int?,
    val pim: Long?,
    val shots: Int?,
    val hits: Int?
)