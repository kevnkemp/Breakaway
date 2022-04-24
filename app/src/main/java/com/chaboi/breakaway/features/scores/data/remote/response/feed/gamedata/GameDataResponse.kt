package com.chaboi.breakaway.features.scores.data.remote.response.feed.gamedata

data class GameDataResponse(
    val game: GameFeedGameResponse?,
    val datetime: GameFeedDatetimeResponse?,
    val status: GameStatusResponse?,
    val teams: GameFeedMatchupResponse?,
)
