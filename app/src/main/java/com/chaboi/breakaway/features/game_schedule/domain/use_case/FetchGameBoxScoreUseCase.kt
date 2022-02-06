package com.chaboi.breakaway.features.game_schedule.domain.use_case

import com.chaboi.breakaway.core.use_case.BaseUseCase
import com.chaboi.breakaway.features.game_schedule.domain.abstractions.GameScheduleRepositoryContract
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameBoxScoreEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchGameBoxScoreUseCase @Inject constructor(
    private val gameScheduleRepository: GameScheduleRepositoryContract
) : BaseUseCase<GameBoxScoreEntity?, FetchGameBoxScoreUseCase.Params>() {
    lateinit var gamePk: String

    override suspend fun run(params: Params): Flow<GameBoxScoreEntity?> {
        return gameScheduleRepository.getGameBoxScore(params.gamePk)
    }

    data class Params(val gamePk: String)
}