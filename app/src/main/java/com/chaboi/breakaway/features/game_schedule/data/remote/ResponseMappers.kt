package com.chaboi.breakaway.features.game_schedule.data.remote

import com.chaboi.breakaway.features.game_schedule.data.remote.response.boxscore.GameBoxScoreResponse
import com.chaboi.breakaway.features.game_schedule.data.remote.response.feed.GameFeedResponse
import com.chaboi.breakaway.features.game_schedule.data.remote.response.schedule.ScheduleResponse
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameBoxScoreEntity
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameFeedEntity
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameScheduleEntity
import com.chaboi.breakaway.util.getDateHourMinutesFormat

// TODO remove usage of domain Entity once we have Room model for this
fun mapGameScheduleResponse(response: ScheduleResponse): List<GameScheduleEntity> {
    return response.dates.firstOrNull()?.games?.map {
        GameScheduleEntity(
            gamePk = it.gamePk,
            awayTeam = it.teams.away.team.name,
            homeTeam = it.teams.home.team.name,
            timeOfGame = it.gameDate.getDateHourMinutesFormat(),
            awayScore = it.teams.away.score,
            homeScore = it.teams.home.score
        )
    } ?: emptyList()
}

fun mapGameBoxScoreResponse(gamePk: String, response: GameBoxScoreResponse): GameBoxScoreEntity? {
    return GameBoxScoreEntity(
        gamePk = gamePk,
        homeTeam = response.teams?.home?.team?.name ?: return null,
        awayTeam = response.teams.away?.team?.name ?: return null,
        homeScore = response.teams.home.teamStats?.teamSkaterStats?.goals ?: return null,
        awayScore = response.teams.away.teamStats?.teamSkaterStats?.goals ?: return null,
        awayShots = response.teams.away.teamStats.teamSkaterStats.shots ?: return null,
        homeShots = response.teams.home.teamStats.teamSkaterStats.shots ?: return null
    )
}

fun mapGameFeedResponse(response: GameFeedResponse): GameFeedEntity? {
    return GameFeedEntity(
        gamePk = response.gameData?.game?.pk ?: return null,
        gameDate = response.gameData.datetime?.dateTime ?: return null,
        gameStatus = response.gameData.status?.detailedState ?: return null,
        homeTeamId = response.gameData.teams?.home?.id ?: return null,
        homeTeam = response.gameData.teams.home.teamName ?: return null,
        homeTeamAbbreviated = response.gameData.teams.home.abbreviation ?: return null,
        awayTeamId = response.gameData.teams.away?.id ?: return null,
        awayTeam = response.gameData.teams.away.teamName ?: return null,
        awayTeamAbbreviated = response.gameData.teams.away.abbreviation ?: return null,
        homeScore = response.liveData?.boxscore?.teams?.home?.teamStats?.teamSkaterStats?.goals ?: return null,
        awayScore = response.liveData.boxscore.teams.away?.teamStats?.teamSkaterStats?.goals ?: return null,
        homeShots = response.liveData.boxscore.teams.home.teamStats.teamSkaterStats.shots ?: return null,
        awayShots = response.liveData.boxscore.teams.away.teamStats.teamSkaterStats.shots ?: return null,
        timeRemaining = response.liveData.linescore?.currentPeriodTimeRemaining ?: return null,
        period = response.liveData.linescore.currentPeriodOrdinal ?: return null
    )
}