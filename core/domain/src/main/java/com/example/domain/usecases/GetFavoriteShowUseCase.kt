package com.example.domain.usecases

import com.example.data.repository.FavoriteShowRepository
import com.example.domain.mapper.toUiShows
import com.example.domain.model.ShowsUi
import javax.inject.Inject

class GetFavoriteShowUseCase @Inject constructor(
    private val favoriteShowRepository: FavoriteShowRepository
) {

    suspend fun getFavoriteListShows(): List<ShowsUi> {
        return favoriteShowRepository.getAllShowsFavoriteList().map { it.toUiShows() }
    }

    suspend fun deleteFavoriteListShows(){
        return favoriteShowRepository.deleteAllShowFromFavoriteList()
    }
}

