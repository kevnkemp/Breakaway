package com.chaboi.breakaway.features.scores.domain.abstractions

import com.chaboi.breakaway.features.scores.domain.entities.GameBoxScoreEntity
import com.chaboi.breakaway.features.scores.domain.entities.GameFeedEntity
import com.chaboi.breakaway.features.scores.domain.entities.GameScheduleEntity
import kotlinx.coroutines.flow.Flow

interface GameScheduleRepositoryContract {

    suspend fun getGameSchedule(date: String): Flow<List<GameScheduleEntity>>

    suspend fun getGameBoxScore(gamePk: String): Flow<GameBoxScoreEntity?>

    suspend fun getLiveGameStats(gamePk: String): Flow<GameFeedEntity?>
}