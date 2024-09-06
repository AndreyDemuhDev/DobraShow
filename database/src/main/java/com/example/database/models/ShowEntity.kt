package com.example.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.database.converter.GenresConverter

@Entity(tableName = "shows_table")
data class ShowsDBO(
    @ColumnInfo("id") @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("ended")
    val ended: String,
    @ColumnInfo("genres")
    @TypeConverters
    val genres: List<String>,
    @Embedded
    val image: ImageShow,
    @ColumnInfo("language")
    val language: String,
    @Embedded val network: NetworkShow,
    @ColumnInfo("officialSite")
    val officialSite: String,
    @ColumnInfo("premiered")
    val premiered: String,
    @Embedded val rating: RatingShow,
    @ColumnInfo("status") val status: String,
    @ColumnInfo("summary")
    val summary: String,
    @ColumnInfo("url")
    val url: String,
) {
    data class ImageShow(
        @ColumnInfo("medium") val medium: String,
        @ColumnInfo("original") val original: String,
    )

    data class NetworkShow(
        @Embedded val country: CountryShow,
        @ColumnInfo("network_show_id") val id: Int,
        @ColumnInfo("network_show_name") val name: String,
        @ColumnInfo("network_show_official_site") val officialSite: String
    )

    data class CountryShow(
        @ColumnInfo("code") val code: String,
        @ColumnInfo("country_show_name") val name: String,
        @ColumnInfo("timezone") val timezone: String
    )

    data class RatingShow(
        @ColumnInfo("average") val average: Double,
    )

}
//
//sealed class ShowStatus(val statusName: String) {
//    object Running : ShowStatus("Running")
//    object Ended : ShowStatus("Ended")
//    object Determined : ShowStatus("To Be Determined")
//    object Unknown : ShowStatus("Unknown")
//}