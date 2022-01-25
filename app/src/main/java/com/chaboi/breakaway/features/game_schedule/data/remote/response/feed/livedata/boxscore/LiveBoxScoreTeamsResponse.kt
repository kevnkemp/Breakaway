package com.chaboi.breakaway.features.game_schedule.data.remote.response.feed.livedata.boxscore

data class LiveBoxScoreTeamsResponse(
    val home: LiveBoxScoreTeamResponse?,
    val away: LiveBoxScoreTeamResponse?
)
