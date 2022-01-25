package com.chaboi.breakaway.features.game_schedule.data

import com.chaboi.breakaway.features.game_schedule.data.remote.GameScheduleRemoteDataSourceContract
import com.chaboi.breakaway.features.game_schedule.data.remote.mapGameBoxScoreResponse
import com.chaboi.breakaway.features.game_schedule.data.remote.mapGameFeedResponse
import com.chaboi.breakaway.features.game_schedule.data.remote.mapGameScheduleResponse
import com.chaboi.breakaway.features.game_schedule.domain.abstractions.GameScheduleRepositoryContract
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameBoxScoreEntity
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameFeedEntity
import com.chaboi.breakaway.features.game_schedule.domain.entities.GameScheduleEntity
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class GameScheduleRepository @Inject constructor(
    private val remoteDataSource: GameScheduleRemoteDataSourceContract
) : GameScheduleRepositoryContract {

    override suspend fun getGameSchedule(): Flow<List<GameScheduleEntity>> =
        flow {
            remoteDataSource.getGamesForDay()
                .suspendOnSuccess {
                    emit(mapGameScheduleResponse(data))
                }
                .suspendOnError {
                    Timber.e("Cannot retrieve games for day! Error Code $statusCode")
                }
                .suspendOnException {
                    Timber.e("Cannot retrieve games for day! Network error")
                }
        }.flowOn(Dispatchers.IO)

    override suspend fun getGameBoxScore(gamePk: String): Flow<GameBoxScoreEntity?> =
       flow {
           remoteDataSource.getGameBoxScore(gamePk)
               .suspendOnSuccess {
                   emit(mapGameBoxScoreResponse(gamePk, data))
               }
               .suspendOnError {
                   Timber.e("Cannot retrieve box score for game $gamePk! Error Code $statusCode")
               }
               .suspendOnException {
                   Timber.e("Cannot retrieve box score for game $gamePk! Network error")
               }
       }.flowOn(Dispatchers.IO)

    override suspend fun getLiveGameStats(gamePk: String): Flow<GameFeedEntity?> =
        flow {
            remoteDataSource.getLiveGameStats(gamePk)
                .suspendOnSuccess {
                    emit(mapGameFeedResponse(data))
                }
                .suspendOnError {
                    Timber.e("Cannot retrieve live stats for game $gamePk! Error Code $statusCode")
                }
                .suspendOnException {
                    Timber.e("Cannot retrieve live stats for game $gamePk! Error: $message")
                }
        }.flowOn(Dispatchers.IO)

}
