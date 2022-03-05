package com.chaboi.breakaway.features.scores.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaboi.breakaway.features.scores.domain.entities.GameFeedEntity
import com.chaboi.breakaway.features.scores.domain.use_case.FetchLiveGameStatsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class ScoreDetailsViewModel @AssistedInject constructor(
    private val fetchLiveGameStatsUseCase: FetchLiveGameStatsUseCase,
    @Assisted gamePk: String
) : ViewModel() {

    init {
        val params = FetchLiveGameStatsUseCase.Params(listOf(gamePk))
        fetchLiveGameStatsUseCase.invoke(viewModelScope, params) { gameFeeds ->
            updateUI(gameFeeds.first())
        }
    }

    private fun updateUI(gameFeed: GameFeedEntity?) {
      // update ui
    }
}