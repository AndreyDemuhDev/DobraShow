package com.example.shows_data

import com.example.shows_data.RequestStatus.Error
import com.example.shows_data.RequestStatus.InProgress
import com.example.shows_data.RequestStatus.Success

interface MergeStrategy<E> {

    fun merge(cache: E, network: E): E
}

internal class DefaultRequestResponseMergeStrategy<T : Any> : MergeStrategy<RequestStatus<T>> {
    override fun merge(cache: RequestStatus<T>, network: RequestStatus<T>): RequestStatus<T> {
        return when {
            cache is InProgress && network is InProgress -> merge(cache, network)
            cache is Success && network is InProgress -> merge(cache, network)
            cache is Error && network is InProgress -> merge(cache, network)
            cache is InProgress && network is Success -> merge(cache, network)
            cache is Success && network is Error -> merge(cache, network)
            cache is Success && network is Success -> merge(cache, network)
            cache is Error && network is Success -> merge(cache, network)
            cache is InProgress && network is Error -> merge(cache, network)
            else -> error("Unknown implementation RequestStatus \n cache = $cache \n network = $network")
        }
    }

    private fun merge(cache: InProgress<T>, network: Error<T>): RequestStatus<T> {
        return Error(data = network.data ?: cache.data, error = network.error)
    }

    private fun merge(cache: InProgress<T>, network: InProgress<T>): RequestStatus<T> {
        return when {
            network.data != null -> InProgress(network.data)
            else -> InProgress(cache.data)
        }
    }

    private fun merge(cache: Success<T>, network: InProgress<T>): RequestStatus<T> {
        return InProgress(cache.data)
    }

    private fun merge(cache: InProgress<T>, network: Success<T>): RequestStatus<T> {
        return InProgress(network.data)
    }

    private fun merge(cache: Success<T>, network: Error<T>): RequestStatus<T> {
        return Error(data = cache.data, error = network.error)
    }

    private fun merge(cache: Success<T>, network: Success<T>): RequestStatus<T> {
        return Success(data = network.data)
    }

    private fun merge(cache: Error<T>, network: InProgress<T>): RequestStatus<T> {
        return cache
    }

    private fun merge(cache: Error<T>, network: Success<T>): RequestStatus<T> {
        return network
    }
}
