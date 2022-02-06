package com.chaboi.breakaway.features.game_schedule.presentation.items

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.chaboi.breakaway.BR
import com.chaboi.breakaway.R
import com.chaboi.breakaway.core.adapter.AdapterItem
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameFeedEntity
import java.util.*
import kotlin.properties.Delegates

class GameItem(val game: GameFeedEntity) : BaseObservable(), AdapterItem {
    override val layoutId = R.layout.game_item
    override val viewType = GameScheduleItemType.GAME.ordinal
}

class GameListItem(
    games: List<AdapterItem>,
    val date: Date,
    val position: Int,
    private val callback: GameScheduleViewModelCallback
) : BaseObservable(), AdapterItem {
    override val layoutId = R.layout.game_list_item
    override val viewType = GameScheduleItemType.GAME_LIST.ordinal

    @get:Bindable
    var isLoading by Delegates.observable(false) { _, _, _ ->
        notifyPropertyChanged(BR.loading)
    }

    @get:Bindable
    var gameFeeds by Delegates.observable(games) { _, _, _ ->
        notifyPropertyChanged(BR.gameFeeds)
    }

    @get:Bindable
    var noGames by Delegates.observable(false) { _, _, _ ->
        notifyPropertyChanged(BR.noGames)
    }

    fun refreshGames() {
        isLoading = true
        callback.refreshGamesForDay(date, position)
    }

    fun updateGames(games: List<AdapterItem>) {
        isLoading = false
        noGames = games.isEmpty()
        gameFeeds = games
    }
}

class EmptyItem : AdapterItem {
    override val layoutId = R.layout.empty_item
    override val viewType = GameScheduleItemType.EMPTY.ordinal
}