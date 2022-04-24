package com.chaboi.breakaway.features.scores.data.remote.response.feed.livedata.boxscore

data class LiveBoxScoreTeamsResponse(
    val home: LiveBoxScoreTeamResponse?,
    val away: LiveBoxScoreTeamResponse?
)
