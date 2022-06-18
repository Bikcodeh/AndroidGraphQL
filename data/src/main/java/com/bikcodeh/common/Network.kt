package com.bikcodeh.common

import com.bikcodeh.domain.common.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> makeSafeNetworkRequest(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    call: suspend () -> T
): Resource<T> {
    return withContext(dispatcher) {
        try {
            Resource.Success(call())
        } catch (exception: Exception) {
            Resource.Error(exception.message)
        }
    }
}