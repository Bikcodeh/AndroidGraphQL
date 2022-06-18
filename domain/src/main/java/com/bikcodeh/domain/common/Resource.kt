package com.bikcodeh.domain.common

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val message: String?) : Resource<T>()
}

inline fun <R, T> Resource<T>.fold(
    onSuccess: (value: T?) -> R,
    onFailure: (error: String?) -> R,
): R = when (this) {
    is Resource.Success -> onSuccess(data)
    is Resource.Error -> onFailure(message)
}