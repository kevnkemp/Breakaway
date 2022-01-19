package com.chaboi.breakaway.features.game_schedule.data.remote

import com.chaboi.breakaway.features.game_schedule.data.remote.response.ScheduleResponse
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameScheduleEntity

// TODO remove usage of domain Entity once we have Room model for this
fun mapGameScheduleResponse(response: ScheduleResponse): List<GameScheduleEntity> {
    return response.dates.first().games.map {
        GameScheduleEntity(
            awayTeam = it.teams.away.team.name,
            homeTeam = it.teams.home.team.name,
            timeOfGame = it.gameDate
        )
    }
}