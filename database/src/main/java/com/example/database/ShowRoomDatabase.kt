package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database.dao.ShowsDao


class ShowsDatabase internal constructor(private val database: ShowRoomDatabase) {

    val showsDao: ShowsDao
        get() = database.showsDao()
}


@Database(entities = [ShowsDao::class], version = 1)
internal abstract class ShowRoomDatabase : RoomDatabase() {

    abstract fun showsDao(): ShowsDao
}

fun ShowsDatabase(applicationContext: Context): ShowsDatabase {
    val showsDatabase = Room.databaseBuilder(
        context = checkNotNull(applicationContext.applicationContext),
        klass = ShowRoomDatabase::class.java,
        name = "shows"
    ).build()
    return ShowsDatabase(showsDatabase)
}