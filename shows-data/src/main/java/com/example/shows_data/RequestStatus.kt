package com.example.shows_data


/**
 *класс который отображает состояние запроса
 *InProgress - запрос в процессе
 *Success - запрос выполнен успешно
 *Error - запрос выполнен с ошибкой
 */
sealed class RequestStatus<out E : Any>(open val data: E? = null) {
    class InProgress<E : Any>(data: E? = null) : RequestStatus<E>(data = data)
    class Success<E : Any>(override val data: E) : RequestStatus<E>(data = data)
    class Error<E : Any>(data: E? = null, val error: Throwable? = null) : RequestStatus<E>(data = data)
}

/**
 *функция расширения к классу статуса запоса
 */
fun <I : Any, O : Any> RequestStatus<I>.mapperStatus(mapper: (I) -> O): RequestStatus<O> {
    return when (this) {
        is RequestStatus.Success -> RequestStatus.Success(mapper(data))
        is RequestStatus.Error -> RequestStatus.Error(data?.let(mapper))
        is RequestStatus.InProgress -> RequestStatus.InProgress(data?.let(mapper))
    }
}

/**
 *функция расширения к классу Result, которая преобразует ответ
 *к нашему классу RequestStatus который характеризует статус выполнения запроса
 */
internal fun <T : Any> Result<T>.toRequestStatus(): RequestStatus<T> {
    return when {
        isSuccess -> RequestStatus.Success(data = getOrThrow())
        isFailure -> RequestStatus.Error()
        else -> error("Unknown data")
    }
}