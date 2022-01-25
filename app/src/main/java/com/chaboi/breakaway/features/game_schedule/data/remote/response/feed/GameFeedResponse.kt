package com.chaboi.breakaway.features.game_schedule.data.remote.response.feed

import com.chaboi.breakaway.features.game_schedule.data.remote.response.feed.gamedata.GameDataResponse
import com.chaboi.breakaway.features.game_schedule.data.remote.response.feed.livedata.LiveDataResponse

data class GameFeedResponse(
    val gameData: GameDataResponse?,
    val liveData: LiveDataResponse?
)
