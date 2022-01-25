package com.chaboi.breakaway.features.game_schedule.domain.use_case

import com.chaboi.breakaway.features.game_schedule.domain.abstractions.GameScheduleRepositoryContract
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameFeedEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchLiveGameStatsUseCase @Inject constructor(
    private val gameScheduleRepository: GameScheduleRepositoryContract
) {
    lateinit var gamePk: String

    fun setup(
        gamePk: String
    ): FetchLiveGameStatsUseCase {
        this.gamePk = gamePk
        return this
    }

    suspend operator fun invoke(): Flow<GameFeedEntity?> =
        gameScheduleRepository.getLiveGameStats(gamePk)
}