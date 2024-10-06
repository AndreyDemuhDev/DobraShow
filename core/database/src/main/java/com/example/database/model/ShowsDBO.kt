package com.example.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

// класс данных для хранения объекта в базе данных
// DBO - data base object

@Entity(tableName = "shows_table")
data class ShowsDBO(
    @ColumnInfo("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("ended")
    val ended: String,
    @ColumnInfo("genres")
    @TypeConverters
    val genres: List<String>,
    @Embedded(prefix = "image")
    val image: ImageShowDBO,
    @ColumnInfo("language")
    val language: String,
    @Embedded(prefix = "network")
    val network: NetworkShowDBO,
    @ColumnInfo("officialSite")
    val officialSite: String,
    @ColumnInfo("premiered")
    val premiered: String,
    @Embedded(prefix = "rating")
    val rating: RatingShowDBO,
    @ColumnInfo("status")
    val status: String,
    @ColumnInfo("summary")
    val summary: String,
    @ColumnInfo("url")
    val url: String,
)

data class ImageShowDBO(
    val medium: String,
    val original: String,
)

data class NetworkShowDBO(
    @Embedded(prefix = "country")
    val country: CountryShowDBO,
    val id: Int,
    val name: String,
    val officialSite: String
)

data class CountryShowDBO(
    val code: String,
    val name: String,
    val timezone: String
)

data class RatingShowDBO(
    val average: Double,
)

