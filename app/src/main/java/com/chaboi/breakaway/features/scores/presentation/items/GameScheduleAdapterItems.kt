package com.chaboi.breakaway.features.scores.presentation.items

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.chaboi.breakaway.BR
import com.chaboi.breakaway.R
import com.chaboi.breakaway.core.adapter.AdapterItem
import com.chaboi.breakaway.core.util.SVG_EXTENSION
import com.chaboi.breakaway.core.util.TEAM_LOGO_URL_LIGHT
import com.chaboi.breakaway.features.scores.domain.entities.GameFeedEntity
import java.util.*
import kotlin.properties.Delegates

class GameItem(
    gameFeed: GameFeedEntity,
    private val callback: ScoresViewModelCallback
) : BaseObservable(), AdapterItem {
    override val layoutId = R.layout.game_item
    override val viewType = GameScheduleItemType.GAME.ordinal

    val awayLogo = "$TEAM_LOGO_URL_LIGHT${gameFeed.awayTeamId}$SVG_EXTENSION"
    val homeLogo = "$TEAM_LOGO_URL_LIGHT${gameFeed.homeTeamId}$SVG_EXTENSION"

    @get:Bindable
    var game by Delegates.observable(gameFeed) { _, _, _ -> notifyPropertyChanged(BR.game) }

    fun updateGameData(gameFeed: GameFeedEntity) {
        game = gameFeed
    }

    fun openGameDetails() {
        callback.openGameDetails(game.gamePk)
    }
}

class GameListItem(
    games: List<AdapterItem>,
    val date: Date,
    val position: Int,
    private val callback: ScoresViewModelCallback
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

    fun updateGameData(games: List<GameFeedEntity>) {
        isLoading = false
        for (game in games) {
            (gameFeeds.find { game.gamePk == (it as GameItem).game.gamePk } as GameItem).updateGameData(game)
        }
    }
}

class EmptyItem : AdapterItem {
    override val layoutId = R.layout.empty_item
    override val viewType = GameScheduleItemType.EMPTY.ordinal
}