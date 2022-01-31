package com.chaboi.breakaway.features.game_schedule.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.cesarferreira.tempo.*
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

    private var isSettled = false

    var currentDate = MutableLiveData(Date())

    var currentPosition = 0

    val pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            val oldPosition = currentPosition
            if (oldPosition != position) {
                val date = currentDate.value ?: Date()
                if (oldPosition > position) {
                    val newDate = date.minus(1.day)
                    refreshGames(newDate)
                    currentDate.postValue(newDate)
                } else if (oldPosition < position) {
                    val newDate = date.plus(1.day)
                    refreshGames(newDate)
                    currentDate.postValue(newDate)
                }
                currentPosition = position
            }

            if (position == 0 && adapter.items.size == 1) {
                val date = currentDate.value
                date?.let {
                    prependItem(GameListItem(emptyList(), date.minus(1.day), this@GameScheduleViewModel))
                    appendItem(GameListItem(emptyList(), date.plus(1.day), this@GameScheduleViewModel))
                }
                currentPosition = 1
                return
            } else if (position == 0) { // init next pages
                val date = (adapter.items[position] as? GameListItem)?.date
                date?.let {
                    prependItem(GameListItem(emptyList(), date.minus(1.day), this@GameScheduleViewModel))
                    currentPosition += position + 1
                }
            } else if (position == adapter.items.size - 1) {
                val date = (adapter.items[position] as? GameListItem)?.date
                date?.let {
                    appendItem(GameListItem(emptyList(), date.plus(1.day), this@GameScheduleViewModel))
                }
            }
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//            if (positionOffset < 0 && position == 0) {
//                val date = currentDate.value
//                date?.let {
//                    prependItem(GameListItem(emptyList(), date.minus(1.day), this@GameScheduleViewModel))
//                    currentPosition.postValue(position + 1)
//                }
//            } else if (positionOffset > 0 && position == adapter.items.size - 1) {
//                val date = currentDate.value
//                date?.let {
//                    appendItem(GameListItem(emptyList(), date.plus(1.day), this@GameScheduleViewModel))
//                    currentPosition.postValue(position + -1)
//                }
//            }
        }

//        override fun onPageScrollStateChanged(state: Int) {
//            super.onPageScrollStateChanged(state)
//            if (state == SCROLL_STATE_DRAGGING) {
//                isSettled = false;
//            }
//            if (state == SCROLL_STATE_SETTLING) {
//                isSettled = true;
//            }
//            if (state == SCROLL_STATE_IDLE && !isSettled) {
//                val position = currentPosition
//                val date = currentDate.value
//                if (position == 0) {
//                    date?.let {
//                        currentPosition = position + 1
//                        prependItem(GameListItem(emptyList(), date.minus(1.day), this@GameScheduleViewModel))
//                    }
//                } else if (position == adapter.items.size - 1) {
//                    date?.let {
//                        appendItem(GameListItem(emptyList(), date.plus(1.day), this@GameScheduleViewModel))
//                    }
//                }
//            }
//        }
    }

    init {
        val date = Date()
        adapter.updateItems(listOf(GameListItem(emptyList(), date, this)))
        refreshGames(date)
    }

    private fun refreshGames(date: Date) {
        currentDate.postValue(date)
        viewModelScope.launch {
            fetchGamesForDayUseCase.setup(date).invoke().collect { games ->
                getLiveDataForGame(date, games)
            }
        }
    }

    private fun getLiveDataForGame(
        date: Date,
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
            val index = adapter.items.indexOfFirst {
                (it as GameListItem).date.beginningOfDay == date.beginningOfDay
            }
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
        var position = currentPosition
        currentDate.postValue(newDate)
        position++
        if (adapter.items.size == (position + 1)) {
            newDate?.let {
                adapter.appendItem(GameListItem(emptyList(), newDate.plus(1.day), this))
                isLoadingNewDay = true
                refreshGames(newDate)
            }
        }
        currentPosition = position
    }

    fun goToPreviousDay() {
        val newDate = currentDate.value?.minus(1.day)
        var position = currentPosition
        currentDate.postValue(newDate)
        if (position == 1) {
            newDate?.let {
                position--
                currentPosition = position // go to first item in adapter
                position++
                isLoadingNewDay = true
                refreshGames(newDate)
                adapter.prependItem(GameListItem(emptyList(), newDate.minus(1.day), this)) // add new first item
                currentPosition = position
            }
        } else {
            position--
            currentPosition = position
        }
    }
}