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

fun <T> Resource<T>.isSuccess(): Boolean = when (this) {
    is Resource.Success -> true
    is Resource.Error -> false
}

fun <T> Resource<T>.isFailure(): Boolean = when (this) {
    is Resource.Success -> false
    is Resource.Error -> true
}
