package com.chaboi.breakaway.features.game_schedule.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesarferreira.tempo.toDate
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameFeedEntity
import com.chaboi.breakaway.features.game_schedule.domain.use_case.FetchGamesForDayUseCase
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameScheduleEntity
import com.chaboi.breakaway.features.game_schedule.domain.use_case.FetchLiveGameStatsUseCase
import com.chaboi.breakaway.util.DATE_FORMAT_UTC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScheduleViewModel @Inject constructor(
    private val fetchGamesForDayUseCase: FetchGamesForDayUseCase,
    private val fetchLiveGameStatsUseCase: FetchLiveGameStatsUseCase
) : ViewModel() {
    val gameEntities = mutableStateListOf<GameFeedEntity>()
    val isRefreshing = MutableStateFlow(true)

    init {
        refreshGames()
    }

    fun refreshGames() {
        isRefreshing.value = true
        viewModelScope.launch {
            fetchGamesForDayUseCase.invoke().collect { games ->
                gameEntities.clear()
                getLiveDataForGame(games)
            }
        }
    }

    private fun getLiveDataForGame(gameScheduleEntities: List<GameScheduleEntity>) {
        // TODO Move to use case
        viewModelScope.launch {
            val boxScores = gameScheduleEntities.map {
                async {
                    fetchLiveGameStatsUseCase.setup(it.gamePk).invoke()
                }
            }
            val feeds = mutableListOf<GameFeedEntity>()
            boxScores.awaitAll().forEach { gameFeed ->
                gameFeed.collect { feed ->
                    feed?.let { feeds.add(feed) }
                }
            }
            gameEntities.addAll(feeds.sortedBy { it.gameDate.toDate(DATE_FORMAT_UTC) })
            isRefreshing.value = false
        }
    }
}