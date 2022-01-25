package com.chaboi.breakaway.features.game_schedule.data.remote.response.feed.livedata.boxscore

data class LiveBoxScoreTeamResponse(
    val team: LiveBoxScoreTeamDetailsResponse?,
    val teamStats: LiveBoxScoreTeamStatsResponse?
)
