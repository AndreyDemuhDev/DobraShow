package com.example.shows_data


interface MergeStrategy<E> {

    fun merge(cache: E, network: E): E

}

internal class DefaultRequestResponseMergeStrategy<T : Any> : MergeStrategy<RequestStatus<T>> {
    override fun merge(cache: RequestStatus<T>, network: RequestStatus<T>): RequestStatus<T> {
        return when {
            cache is RequestStatus.InProgress && network is RequestStatus.InProgress -> mergeProgressCacheAndProgressNetwork(
                cache,
                network
            )

            cache is RequestStatus.Success && network is RequestStatus.InProgress -> mergeSuccessCacheAndProgressNetwork(
                cache,
                network
            )

            cache is RequestStatus.InProgress && network is RequestStatus.Success -> mergeProgressCacheAndSuccessNetwork(
                cache,
                network
            )

            cache is RequestStatus.Success && network is RequestStatus.Error -> mergeSuccessCacheAndErrorNetwork(
                cache,
                network
            )

            cache is RequestStatus.Success && network is RequestStatus.Success -> mergeSuccessCacheAndSuccessNetwork(
                cache,
                network
            )

            else -> error("Unknown implementation RequestStatus \n cache = $cache \n network = $network")
        }

    }

    private fun mergeProgressCacheAndProgressNetwork(
        cache: RequestStatus.InProgress<T>,
        network: RequestStatus.InProgress<T>
    ): RequestStatus<T> {
        return when {
            network.data != null -> RequestStatus.InProgress(network.data)
            else ->
                RequestStatus.InProgress(cache.data)
        }
    }

    private fun mergeSuccessCacheAndProgressNetwork(
        cache: RequestStatus.Success<T>,
        network: RequestStatus.InProgress<T>
    ): RequestStatus<T> {
        return RequestStatus.InProgress(cache.data)
    }

    private fun mergeProgressCacheAndSuccessNetwork(
        cache: RequestStatus.InProgress<T>,
        network: RequestStatus.Success<T>
    ): RequestStatus<T> {
        return RequestStatus.InProgress(network.data)
    }

    private fun mergeSuccessCacheAndErrorNetwork(
        cache: RequestStatus.Success<T>,
        network: RequestStatus.Error<T>
    ): RequestStatus<T> {
        return RequestStatus.Error(data = cache.data, error = network.error)
    }

    private fun mergeSuccessCacheAndSuccessNetwork(
        cache: RequestStatus.Success<T>,
        network: RequestStatus.Success<T>
    ): RequestStatus<T> {
        return RequestStatus.Success(data = network.data)
    }


}