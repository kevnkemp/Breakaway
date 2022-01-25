package com.chaboi.breakaway.features.game_schedule.domain.use_case

import com.chaboi.breakaway.features.game_schedule.domain.abstractions.GameScheduleRepositoryContract
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameBoxScoreEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchGameBoxScoreUseCase @Inject constructor(
    private val gameScheduleRepository: GameScheduleRepositoryContract
) {
    lateinit var gamePk: String

    fun setup(
        gamePk: String
    ): FetchGameBoxScoreUseCase {
        this.gamePk = gamePk
        return this
    }

    suspend operator fun invoke(): Flow<GameBoxScoreEntity?> = gameScheduleRepository.getGameBoxScore(gamePk)
}