package com.chaboi.breakaway.features.scores.domain.use_case

import com.chaboi.breakaway.core.use_case.BaseUseCase
import com.chaboi.breakaway.features.scores.domain.abstractions.GameScheduleRepositoryContract
import com.chaboi.breakaway.features.scores.domain.entities.GameFeedEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchLiveGameStatsUseCase @Inject constructor(
    private val gameScheduleRepository: GameScheduleRepositoryContract
) : BaseUseCase<List<GameFeedEntity?>, FetchLiveGameStatsUseCase.Params>() {

    override suspend fun run(params: Params): Flow<List<GameFeedEntity?>> {
        val feeds = mutableListOf<GameFeedEntity>()
        val gameFeedsDeferred = params.gamePks.map {
            coroutineScope {
                async {
                    gameScheduleRepository.getLiveGameStats(it)
                }
            }
        }
        gameFeedsDeferred.awaitAll().forEach { gameFeed ->
            gameFeed.collect { feed ->
                feed?.let { feeds.add(feed) }
            }
        }
        return flow { emit(feeds) }
    }

    data class Params(val gamePks: List<String>)
}