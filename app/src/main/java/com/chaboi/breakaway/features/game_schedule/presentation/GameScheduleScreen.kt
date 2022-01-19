package com.chaboi.breakaway.features.game_schedule.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier


@Composable
fun GameScheduleScreen() {
    val gameScheduleViewModel: GameScheduleViewModel = viewModel()
    Column {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp)
        ) {
            items(gameScheduleViewModel.gameEntities) { gameScheduleEntity ->
                GameScheduleItem(gameScheduleEntity)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { gameScheduleViewModel.refreshGames() }) {
            Text(text = "Refresh Games")
        }
    }
}