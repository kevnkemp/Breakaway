package com.chaboi.breakaway.features.scores.domain.entities

data class GameEntity(
    val gameScheduleEntity: GameScheduleEntity,
    val gameBoxScoreEntity: GameBoxScoreEntity
)
