package com.chaboi.breakaway.features.game_schedule.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameScheduleEntity

@Composable
fun GameScheduleItem(
    // modifier: Modifier, // TODO pass in Modifier
    entity: GameScheduleEntity
) {
    Card(
        elevation = 3.dp,
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
    ) {
       Row {
           Column {
               Text(text = entity.awayTeam)
               Text(text = entity.homeTeam)
           }
           Spacer(modifier = Modifier.width(10.dp))
           Column {
               Text(text = entity.timeOfGame)
           }
       }
    }
}