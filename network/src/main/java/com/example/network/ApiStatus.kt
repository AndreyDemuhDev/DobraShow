package com.example.network

sealed interface ApiStatus<T> {
    data class SuccessStatus<T>(val data: T) : ApiStatus<T>
    data class ExceptionStatus<T>(val exception: Exception) : ApiStatus<T>

    fun onSuccess(block: (T) -> Unit): ApiStatus<T> {
        if (this is SuccessStatus) block(data)
        return this
    }

    fun onException(block: (Exception) -> Unit): ApiStatus<T> {
        if (this is ExceptionStatus) block(exception)
        return this
    }
}