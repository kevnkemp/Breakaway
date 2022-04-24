package com.chaboi.breakaway.features.scores.data.remote.response.feed.livedata.plays

data class PlayAboutResponse(
    val eventIdx: Int?,
    val eventId: Int?,
    val period: Int?,
    val periodType: String?,
    val ordinalNum: String?,
    val periodTime: String?,
    val periodTimeRemaining: String?
)