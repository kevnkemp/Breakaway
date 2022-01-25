package com.chaboi.breakaway.features.game_schedule.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.cesarferreira.tempo.toDate
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameEntity
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameFeedEntity
import com.chaboi.breakaway.util.FINAL
import com.chaboi.breakaway.util.IN_PROGRESS
import com.chaboi.breakaway.util.getDateHourMinutesFormat
import com.chaboi.breakaway.util.getDateMonthDayFormat

@Composable
fun GameScheduleItem(gameFeed: GameFeedEntity) {
    Card(
        elevation = 3.dp,
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
    ) {
        ConstraintLayout {
            val (timeAndTeams, scores) = createRefs()

            Column(
                modifier = Modifier.constrainAs(timeAndTeams){
                    top.linkTo(parent.top, margin = 4.dp)
                    start.linkTo(parent.start, margin = 4.dp)
                }
            ) {
                Text(
                    text = when (gameFeed.gameStatus) {
                        FINAL -> FINAL
                        IN_PROGRESS -> "${gameFeed.timeRemaining} | ${gameFeed.period}"
                        else -> gameFeed.gameDate.getDateHourMinutesFormat()
                    }
                )
                Text(
                    text = gameFeed.awayTeamAbbreviated,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Text(
                    text = "SOG: ${gameFeed.awayShots}",
                    fontSize = 12.sp
                )
                Text(
                    text = gameFeed.homeTeamAbbreviated,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "SOG: ${gameFeed.homeShots}",
                    fontSize = 12.sp
                )
            }
            Column(
                modifier = Modifier.constrainAs(scores) {
                    top.linkTo(parent.top, margin = 4.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                }
            ) {
                Text(
                    text = gameFeed.gameDate.getDateMonthDayFormat(),
                    textAlign = TextAlign.End
                )
                Text(
                    text = gameFeed.awayScore.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.End
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = gameFeed.homeScore.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.End
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}