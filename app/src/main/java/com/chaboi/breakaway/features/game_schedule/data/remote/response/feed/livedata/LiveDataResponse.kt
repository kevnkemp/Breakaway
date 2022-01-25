package com.chaboi.breakaway.features.game_schedule.data.remote.response.feed.livedata

import com.chaboi.breakaway.features.game_schedule.data.remote.response.feed.livedata.boxscore.LiveBoxScoreResponse
import com.chaboi.breakaway.features.game_schedule.data.remote.response.feed.livedata.linescore.LineScoreResponse
import com.chaboi.breakaway.features.game_schedule.data.remote.response.feed.livedata.plays.PlayResponse

data class LiveDataResponse(
    val plays: PlayResponse?,
    val linescore: LineScoreResponse?,
    val boxscore: LiveBoxScoreResponse?
)