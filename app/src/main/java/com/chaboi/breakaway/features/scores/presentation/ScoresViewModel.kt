package com.chaboi.breakaway.features.scores.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.cesarferreira.tempo.*
import com.chaboi.breakaway.core.adapter.AdapterItem
import com.chaboi.breakaway.core.adapter.INITIAL_POSITION
import com.chaboi.breakaway.core.adapter.InfiniteBindableAdapter
import com.chaboi.breakaway.core.adapter.TOTAL_ITEMS
import com.chaboi.breakaway.features.scores.domain.entities.GameFeedEntity
import com.chaboi.breakaway.features.scores.domain.entities.GameScheduleEntity
import com.chaboi.breakaway.features.scores.domain.use_case.FetchGamesForDayUseCase
import com.chaboi.breakaway.features.scores.domain.use_case.FetchLiveGameStatsUseCase
import com.chaboi.breakaway.features.scores.presentation.items.EmptyItem
import com.chaboi.breakaway.features.scores.presentation.items.GameItem
import com.chaboi.breakaway.features.scores.presentation.items.GameListItem
import com.chaboi.breakaway.features.scores.presentation.items.ScoresViewModelCallback
import com.chaboi.breakaway.util.DATE_FORMAT_UTC
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ScoresViewModel @Inject constructor(
    private val fetchGamesForDayUseCase: FetchGamesForDayUseCase,
    private val fetchLiveGameStatsUseCase: FetchLiveGameStatsUseCase
) : ViewModel(), ScoresViewModelCallback {

    val adapter = InfiniteBindableAdapter()

    var callback: ScoresActionCallback? = null

    val currentDate: LiveData<Date>
        get() = _currentDate
    private val _currentDate = MutableLiveData(Date())

    val goToPrevious = MutableLiveData(false)
    val goToNext = MutableLiveData(false)

    private var currentPosition = INITIAL_POSITION

    val pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            goToPrevious.postValue(false)
            goToNext.postValue(false)
            val oldPosition = currentPosition
            if (oldPosition != position) {
                updatePageOnSwitch(oldPosition, position)
            }
        }
    }

    init {
        val date = _currentDate.value
        date?.let {
            val list = MutableList<AdapterItem>(TOTAL_ITEMS) { EmptyItem() }
            adapter.updateItems(list)
            refreshGames(date, INITIAL_POSITION)
        }
    }

    private fun updatePageOnSwitch(oldPosition: Int, newPosition: Int) {
        val date = _currentDate.value
        currentPosition = newPosition
        if (oldPosition > newPosition) {
            date?.let {
                val newDate = date.minus(1.day)
                _currentDate.postValue(newDate)
                if (isPageEmpty(newPosition)) {
                    refreshGames(newDate, newPosition)
                }
            }
        } else if (oldPosition < newPosition) {
            date?.let {
                val newDate = date.plus(1.day)
                _currentDate.postValue(newDate)
                if (isPageEmpty(newPosition)) {
                    refreshGames(newDate, newPosition)
                }
            }
        }
    }

    private fun isPageEmpty(position: Int): Boolean {
        return (adapter.items[position] is EmptyItem)
    }

    private fun refreshGames(date: Date, position: Int) {
        val params = FetchGamesForDayUseCase.Params(date)
        fetchGamesForDayUseCase.invoke(viewModelScope, params) { games ->
            if (games.isEmpty()) {
                updateItemWithNoGames(date, position)
            } else {
                getLiveDataForGame(date, position, games)
            }
        }
    }

    private fun updateItemWithNoGames(date: Date, position: Int) {
        if (adapter.items[position] !is GameListItem) {
            adapter.replaceItem(position, GameListItem(emptyList(), date, position, this))
            (adapter.items[position] as GameListItem).updateGames(emptyList())
        } else {
            (adapter.items[position] as GameListItem).updateGames(emptyList())
        }
    }

    private fun getLiveDataForGame(
        date: Date,
        position: Int,
        gameScheduleEntities: List<GameScheduleEntity>
    ) {
        val params = FetchLiveGameStatsUseCase.Params(gameScheduleEntities.map { it.gamePk })
        fetchLiveGameStatsUseCase.invoke(viewModelScope, params) { gameFeeds ->
            updatePage(date, position, gameFeeds.filterNotNull())
        }
    }

    private fun updatePage(date: Date, position: Int, feeds: List<GameFeedEntity>) {
        val sortedFeeds = createAdapterList(feeds)
        if (adapter.items[position] !is GameListItem) {
            adapter.replaceItem(position, GameListItem(sortedFeeds, date, position, this))
        } else {
            val gameList = adapter.items[position] as? GameListItem
            gameList?.updateGameData(feeds)
        }
        adapter.notifyItemChanged(position)
    }

    private fun createAdapterList(gameFeeds: List<GameFeedEntity>): List<AdapterItem> {
        return gameFeeds
            .sortedBy { it.gameDate.toDate(DATE_FORMAT_UTC) }
            .map {
                GameItem(it, this)
            }
    }

    override fun refreshGamesForDay(date: Date, position: Int) {
        refreshGames(date, position)
    }

    override fun openGameDetails(gamePk: String) {
        callback?.openGameDetails(gamePk)
    }

    fun goToNextDay() {
        goToPrevious.postValue(false)
        goToNext.postValue(true)
    }

    fun goToPreviousDay() {
        goToNext.postValue(false)
        goToPrevious.postValue(true)
    }
}