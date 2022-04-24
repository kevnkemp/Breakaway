package com.chaboi.breakaway.features.scores.presentation.items

import java.util.*

interface ScoresViewModelCallback {
    fun refreshGamesForDay(date: Date, position: Int)
    fun openGameDetails(gamePk: String)
}