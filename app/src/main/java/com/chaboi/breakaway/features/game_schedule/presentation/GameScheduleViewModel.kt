package com.chaboi.breakaway.features.game_schedule.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cesarferreira.tempo.day
import com.cesarferreira.tempo.minus
import com.cesarferreira.tempo.plus
import com.cesarferreira.tempo.toDate
import com.chaboi.breakaway.core.adapter.AdapterItem
import com.chaboi.breakaway.core.adapter.BindableAdapter
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameFeedEntity
import com.chaboi.breakaway.features.game_schedule.domain.use_case.FetchGamesForDayUseCase
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameScheduleEntity
import com.chaboi.breakaway.features.game_schedule.domain.use_case.FetchLiveGameStatsUseCase
import com.chaboi.breakaway.features.game_schedule.presentation.items.GameItem
import com.chaboi.breakaway.features.game_schedule.presentation.items.GameListItem
import com.chaboi.breakaway.features.game_schedule.presentation.items.GameScheduleViewModelCallback
import com.chaboi.breakaway.util.DATE_FORMAT_UTC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GameScheduleViewModel @Inject constructor(
    private val fetchGamesForDayUseCase: FetchGamesForDayUseCase,
    private val fetchLiveGameStatsUseCase: FetchLiveGameStatsUseCase
) : ViewModel(), GameScheduleViewModelCallback {

    val adapter = BindableAdapter()

    var isLoadingNewDay = false

    var currentDate = MutableLiveData(Date())

    var currentPosition = MutableLiveData(0)

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManger = recyclerView.layoutManager as? LinearLayoutManager ?: return
//            if (dx > 0) { // scroll to the right
//                if (!isLoadingNewDay) goToNextDay()
//            } else if (dx < 0) { // scroll to the left
//                if (!isLoadingNewDay) goToPreviousDay()
//            }
        }
    }

    init {
        val date = Date()
        adapter.updateItems(
            listOf(
                GameListItem(emptyList(), date.minus(1.day), this),
                GameListItem(emptyList(), date, this),
                GameListItem(emptyList(), date.plus(1.day), this)
            )
        )
        currentPosition.postValue(1)
        refreshGames(date, 1)
    }

    private fun refreshGames(date: Date, index: Int = 0) {
        currentDate.postValue(date)
        viewModelScope.launch {
            fetchGamesForDayUseCase.setup(date).invoke().collect { games ->
                getLiveDataForGame(index, games)
            }
        }
    }

    private fun getLiveDataForGame(
        index: Int,
        gameScheduleEntities: List<GameScheduleEntity>
    ) {
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
            val gameList = adapter.items[index] as? GameListItem
            gameList?.updateGames(sortedFeeds)
            isLoadingNewDay = false
        }
    }

    private fun createAdapterList(gameFeeds: List<GameFeedEntity>): List<AdapterItem> {
        return gameFeeds
            .sortedBy { it.gameDate.toDate(DATE_FORMAT_UTC) }
            .map {
                GameItem(it)
            }
    }

    override fun refreshGamesForDay(date: Date) {
        refreshGames(date)
    }

    fun prependItem(item: AdapterItem) {
        adapter.prependItem(item)
    }

    fun appendItem(item: AdapterItem) {
        adapter.appendItem(item)
    }

    fun goToNextDay() {
        val newDate = currentDate.value?.plus(1.day)
        var position = currentPosition.value ?: return
        currentDate.postValue(newDate)
        position++
        if (adapter.items.size == (position + 1)) {
            newDate?.let {
                adapter.appendItem(GameListItem(emptyList(), newDate.plus(1.day), this))
                isLoadingNewDay = true
                refreshGames(newDate, position)
            }
        }
        currentPosition.postValue(position)
    }

    fun goToPreviousDay() {
        val newDate = currentDate.value?.minus(1.day)
        var position = currentPosition.value ?: return
        currentDate.postValue(newDate)
        if (position == 1) {
            newDate?.let {
                position--
                currentPosition.postValue(position) // go to first item in adapter
                position++
                isLoadingNewDay = true
                refreshGames(newDate, position)
                adapter.prependItem(GameListItem(emptyList(), newDate.minus(1.day), this)) // add new first item
                currentPosition.postValue(position)
            }
        } else {
            position--
            currentPosition.postValue(position)
        }
    }
}