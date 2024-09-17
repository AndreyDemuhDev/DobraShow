package com.example.shows_data

import com.example.shows_data.RequestStatus.Success
import com.example.shows_data.RequestStatus.Error
import com.example.shows_data.RequestStatus.InProgress


interface MergeStrategy<E> {

    fun merge(cache: E, network: E): E

}

internal class DefaultRequestResponseMergeStrategy<T : Any> : MergeStrategy<RequestStatus<T>> {
    override fun merge(cache: RequestStatus<T>, network: RequestStatus<T>): RequestStatus<T> {
        return when {
            cache is InProgress && network is InProgress -> mergeProgressCacheAndProgressNetwork(
                cache,
                network
            )

            cache is Success && network is InProgress -> mergeSuccessCacheAndProgressNetwork(
                cache,
                network
            )

            cache is Error && network is InProgress -> mergeErrorCacheAndProgressNetwork(
                cache,
                network
            )

            cache is InProgress && network is Success -> mergeProgressCacheAndSuccessNetwork(
                cache,
                network
            )

            cache is Success && network is Error -> mergeSuccessCacheAndErrorNetwork(
                cache,
                network
            )

            cache is Success && network is Success -> mergeSuccessCacheAndSuccessNetwork(
                cache,
                network
            )

            cache is Error && network is Success -> mergeErrorCacheAndSuccessNetwork(
                cache,
                network
            )

            else -> error("Unknown implementation RequestStatus \n cache = $cache \n network = $network")
        }

    }

    private fun mergeProgressCacheAndProgressNetwork(
        cache: InProgress<T>,
        network: InProgress<T>
    ): RequestStatus<T> {
        return when {
            network.data != null -> InProgress(network.data)
            else -> InProgress(cache.data)
        }
    }

    private fun mergeSuccessCacheAndProgressNetwork(
        cache: Success<T>,
        network: InProgress<T>
    ): RequestStatus<T> {
        return InProgress(cache.data)
    }

    private fun mergeProgressCacheAndSuccessNetwork(
        cache: InProgress<T>,
        network: Success<T>
    ): RequestStatus<T> {
        return InProgress(network.data)
    }

    private fun mergeSuccessCacheAndErrorNetwork(
        cache: Success<T>,
        network: Error<T>
    ): RequestStatus<T> {
        return Error(data = cache.data, error = network.error)
    }

    private fun mergeSuccessCacheAndSuccessNetwork(
        cache: Success<T>,
        network: Success<T>
    ): RequestStatus<T> {
        return Success(data = network.data)
    }

    private fun mergeErrorCacheAndProgressNetwork(
        cache: Error<T>,
        network: InProgress<T>
    ): RequestStatus<T> {
        return InProgress(data = network.data)
    }

    private fun mergeErrorCacheAndSuccessNetwork(
        cache: Error<T>,
        network: Success<T>
    ): RequestStatus<T> {
        return Success(data = network.data)
    }


}