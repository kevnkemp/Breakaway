package com.chaboi.breakaway.features.game_schedule.presentation.items

import java.util.*

interface GameScheduleViewModelCallback {
    fun refreshGamesForDay(date: Date)
}