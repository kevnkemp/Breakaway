package com.chaboi.breakaway.features.scores.domain.use_case

import com.chaboi.breakaway.core.use_case.BaseUseCase
import com.chaboi.breakaway.features.scores.domain.abstractions.GameScheduleRepositoryContract
import com.chaboi.breakaway.features.scores.domain.entities.GameScheduleEntity
import com.chaboi.breakaway.util.formatYearMonthDay
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class FetchGamesForDayUseCase @Inject constructor(
    private val gameScheduleRepository: GameScheduleRepositoryContract
): BaseUseCase<List<GameScheduleEntity>, FetchGamesForDayUseCase.Params>() {

    override suspend fun run(params: Params): Flow<List<GameScheduleEntity>> {
        return gameScheduleRepository.getGameSchedule(params.date.formatYearMonthDay())
    }

    data class Params(val date: Date)
}