package com.example.shows_data

sealed class RequestStatus<out E>(internal val data: E? = null) {
    class InProgress<E>(data: E? = null) : RequestStatus<E>(data = data)
    class Success<E : Any>(data: E) : RequestStatus<E>(data = data)
    class Error<E>(data: E? = null) : RequestStatus<E>()
}

internal fun <I, O> RequestStatus<I>.map(mapper: (I) -> O): RequestStatus<O> {
    return when (this) {
        is RequestStatus.Success -> {
            val outData: O = mapper(checkNotNull(data))
            RequestStatus.Success(checkNotNull(outData))
        }

        is RequestStatus.Error -> RequestStatus.Error(data?.let(mapper))
        is RequestStatus.InProgress -> RequestStatus.InProgress(data?.let(mapper))
    }
}

internal fun <T> Result<T>.toRequestStatus(): RequestStatus<T> {
    return when {
        isSuccess -> RequestStatus.Success(data = checkNotNull(getOrThrow()))
        isFailure -> RequestStatus.Error()
        else -> error("Unknown data")
    }
}