package com.chaboi.breakaway.features.game_schedule.presentation.items

import com.chaboi.breakaway.R
import com.chaboi.breakaway.core.adapter.AdapterItem
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameFeedEntity

class GameItem(val game: GameFeedEntity) : AdapterItem {
    override val layoutId = R.layout.game_item
    override val viewType = GameScheduleItemType.GAME.ordinal
}