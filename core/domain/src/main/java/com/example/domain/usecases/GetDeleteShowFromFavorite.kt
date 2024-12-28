package com.example.domain.usecases

import com.example.data.model.ShowEntity
import com.example.data.repository.DetailShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetDeleteShowFromFavorite @Inject constructor(
    private val detailShowRepository: DetailShowRepository
) {

    operator fun invoke(show: ShowEntity) = flow<Unit> {
        detailShowRepository.deleteShowFromFavorite(show = show)
    }.flowOn(Dispatchers.IO)

}