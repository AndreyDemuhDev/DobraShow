package com.example.data.repository

import com.example.data.mapper.toShow
import com.example.data.model.ShowEntity
import com.example.database.ShowsDatabase
import javax.inject.Inject

class FavoriteShowRepository @Inject constructor(
    private val database: ShowsDatabase
) {

    suspend fun getAllShowsFavoriteList(): List<ShowEntity> {
        return database.showsDao.getFavoriteShowList().map { it.toShow() }
    }

    suspend fun deleteAllShowFromFavoriteList() {
        database.showsDao.deleteAllFavoriteShows()
    }
}