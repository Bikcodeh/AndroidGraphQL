package com.bikcodeh.domain.common

import androidx.annotation.StringRes

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    @StringRes val messageId: Int = -1
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
    class ErrorResource<T>(@StringRes messageId: Int, data: T? = null) :
        Resource<T>(data = data, messageId = messageId)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}