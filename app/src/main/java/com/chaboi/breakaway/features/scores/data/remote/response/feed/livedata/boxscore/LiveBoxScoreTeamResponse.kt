package com.chaboi.breakaway.features.scores.data.remote.response.feed.livedata.boxscore

data class LiveBoxScoreTeamResponse(
    val team: LiveBoxScoreTeamDetailsResponse?,
    val teamStats: LiveBoxScoreTeamStatsResponse?
)
