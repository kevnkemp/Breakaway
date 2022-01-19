package com.chaboi.breakaway.features.game_schedule.domain

import com.chaboi.breakaway.features.game_schedule.domain.abstractions.GameScheduleRepositoryContract
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameScheduleEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameGamesForDayUseCase @Inject constructor(
    private val gameScheduleRepository: GameScheduleRepositoryContract
) {
    suspend operator fun invoke(): Flow<List<GameScheduleEntity>> = gameScheduleRepository.getGameSchedule()
}