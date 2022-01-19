package com.chaboi.breakaway.features.game_schedule.domain.abstractions

import com.chaboi.breakaway.features.game_schedule.domain.entities.GameScheduleEntity
import kotlinx.coroutines.flow.Flow

interface GameScheduleRepositoryContract {

    suspend fun getGameSchedule(): Flow<List<GameScheduleEntity>>
}