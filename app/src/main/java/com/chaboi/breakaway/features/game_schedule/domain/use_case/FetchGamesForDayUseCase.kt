package com.chaboi.breakaway.features.game_schedule.domain.use_case

import com.chaboi.breakaway.core.util.EMPTY_STRING
import com.chaboi.breakaway.features.game_schedule.domain.abstractions.GameScheduleRepositoryContract
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameScheduleEntity
import com.chaboi.breakaway.util.formatYearMonthDay
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class FetchGamesForDayUseCase @Inject constructor(
    private val gameScheduleRepository: GameScheduleRepositoryContract
) {
    var date = EMPTY_STRING

    fun setup(date: Date): FetchGamesForDayUseCase {
        this.date = date.formatYearMonthDay()
        return this
    }

    suspend operator fun invoke(): Flow<List<GameScheduleEntity>> = gameScheduleRepository.getGameSchedule(date)
}