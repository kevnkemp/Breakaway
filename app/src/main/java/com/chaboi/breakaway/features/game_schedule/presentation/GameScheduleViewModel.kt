package com.chaboi.breakaway.features.game_schedule.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesarferreira.tempo.toDate
import com.chaboi.breakaway.core.adapter.AdapterItem
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameFeedEntity
import com.chaboi.breakaway.features.game_schedule.domain.use_case.FetchGamesForDayUseCase
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameScheduleEntity
import com.chaboi.breakaway.features.game_schedule.domain.use_case.FetchLiveGameStatsUseCase
import com.chaboi.breakaway.features.game_schedule.presentation.items.GameItem
import com.chaboi.breakaway.util.DATE_FORMAT_UTC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScheduleViewModel @Inject constructor(
    private val fetchGamesForDayUseCase: FetchGamesForDayUseCase,
    private val fetchLiveGameStatsUseCase: FetchLiveGameStatsUseCase
) : ViewModel() {
    val gameFeeds: LiveData<List<AdapterItem>>
        get() = _gameFeeds
    private val _gameFeeds = MutableLiveData<List<AdapterItem>>(emptyList())

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData(false)

    init {
        refreshGames()
    }

    fun refreshGames() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            fetchGamesForDayUseCase.invoke().collect { games ->
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
            val sortedFeeds = createAdapterList(feeds)
            _gameFeeds.postValue(sortedFeeds)
            _isLoading.postValue(false)
        }
    }

    private fun createAdapterList(gameFeeds: List<GameFeedEntity>): List<AdapterItem> {
        return gameFeeds
            .sortedBy { it.gameDate.toDate(DATE_FORMAT_UTC) }
            .map {
                GameItem(it)
            }
    }
}