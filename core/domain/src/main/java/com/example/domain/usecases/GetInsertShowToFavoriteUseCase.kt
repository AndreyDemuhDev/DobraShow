package com.example.domain.usecases

import com.example.data.model.ShowEntity
import com.example.data.repository.DetailShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetInsertShowToFavoriteUseCase @Inject constructor(
    private val detailShowRepository: DetailShowRepository
) {
    operator fun invoke(show:ShowEntity) = flow<Unit> {
        detailShowRepository.insertShowToFavorite(show = show)
    }.flowOn(Dispatchers.IO)

}