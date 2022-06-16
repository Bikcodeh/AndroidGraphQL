package com.bikcodeh.common

import com.bikcodeh.data.R
import com.bikcodeh.domain.common.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeSafeNetworkRequest(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    call: suspend () -> T
): Resource<T> {
    return withContext(dispatcher) {
        Resource.Loading(data = null)
        try {
            Resource.Success(call())
        } catch (exception: UnknownHostException) {
            Resource.ErrorResource(R.string.error_connection)
        } catch (exception: Exception) {
            Resource.Error(exception.message)
        }
    }
}