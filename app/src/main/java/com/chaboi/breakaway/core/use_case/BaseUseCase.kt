package com.chaboi.breakaway.core.use_case

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseUseCase<out Type, in Params> where Type : Any? {

    abstract suspend fun run(params: Params): Flow<Type>

    open operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        onResult: (Type) -> Unit = {}
    ) {
        val backgroundJob = scope.async { run(params) }
        scope.launch {
            onResult(backgroundJob.await().first()) // TODO refactor or make new use cases so we can handle auto-refresh
        }
    }
}