package com.example.domain.usecases

import com.example.data.repository.FavoriteShowRepository
import javax.inject.Inject

class GetAllShowFromFavorite @Inject constructor(
    private val favoriteShowRepository: FavoriteShowRepository
) {

    suspend operator fun invoke() = favoriteShowRepository.getAllShowsFavoriteList()

}