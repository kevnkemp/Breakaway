package com.chaboi.breakaway.features.game_schedule.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaboi.breakaway.features.game_schedule.domain.GameGamesForDayUseCase
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameScheduleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScheduleViewModel @Inject constructor(
    private val getGamesForDayUseCase: GameGamesForDayUseCase
) : ViewModel() {
    val gameEntities = mutableStateListOf<GameScheduleEntity>()
    init {
        refreshGames()
    }
    fun refreshGames() =
        viewModelScope.launch {
            getGamesForDayUseCase.invoke().collect {
                gameEntities.clear()
                gameEntities.addAll(it)
            }
        }
}