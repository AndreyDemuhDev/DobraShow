package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.models.ShowsDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowsDao {

    @Query("SELECT * FROM shows_table")
    fun getAllListShow(): Flow<List<ShowsDBO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShowToDatabase(show: List<ShowsDBO>)

    @Delete
    suspend fun removeShowFromDatabase(shows: List<ShowsDBO>)

    @Query("DELETE FROM shows_table")
    suspend fun deleteAllShowFromDatabase()


}