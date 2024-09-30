package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.converter.GenresConverter
import com.example.database.dao.ShowsDao
import com.example.database.model.ShowsDBO

class ShowsDatabase internal constructor(private val database: AppRoomDatabase) {

    val showsDao: ShowsDao
        get() = database.showsDao()
}

@Database(entities = [ShowsDBO::class], version = 1, exportSchema = false)
@TypeConverters(GenresConverter::class)
internal abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun showsDao(): ShowsDao
}

fun showsAppDatabase(applicationContext: Context): ShowsDatabase {
    val showsDatabase = Room.databaseBuilder(
        context = checkNotNull(applicationContext.applicationContext),
        klass = AppRoomDatabase::class.java,
        name = "shows"
    ).build()
    return ShowsDatabase(showsDatabase)
}