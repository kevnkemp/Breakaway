package com.chaboi.breakaway.features.scores.data.remote.response.feed.gamedata

data class GameStatusResponse(
    val abstractGameState: String?,
    val codedGameState: String?,
    val detailedState: String?,
    val statusCode: String?,
    val startTimeTBD: Boolean?
)
