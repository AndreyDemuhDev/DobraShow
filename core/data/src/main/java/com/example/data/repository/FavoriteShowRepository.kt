package com.example.data.repository

import com.example.database.ShowsDatabase
import javax.inject.Inject

class FavoriteShowRepository @Inject constructor(
    private val database: ShowsDatabase
) {

}