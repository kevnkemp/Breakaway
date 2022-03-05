package com.chaboi.breakaway.features.scores.presentation

import dagger.assisted.AssistedFactory

@AssistedFactory
interface ScoresDetailsViewModelFactory {
    fun create(gamePk: String): ScoreDetailsViewModel
}