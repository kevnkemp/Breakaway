package com.chaboi.breakaway.features.game_schedule.presentation.items

import java.util.*

interface ScoresViewModelCallback {
    fun refreshGamesForDay(date: Date, position: Int)
}