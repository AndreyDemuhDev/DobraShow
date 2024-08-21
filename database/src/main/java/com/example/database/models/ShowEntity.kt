package com.example.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shows_table")
data class ShowsDBO(
    @ColumnInfo("id") @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("ended") val ended: String,
    @ColumnInfo("genres") val genres: List<String>,
    @ColumnInfo("image") @Embedded val image: ImageShow,
    @ColumnInfo("language") val language: String,
    @ColumnInfo("network") @Embedded val network: NetworkShow,
    @ColumnInfo("officialSite") val officialSite: String,
    @ColumnInfo("premiered") val premiered: String,
    @ColumnInfo("rating") @Embedded val rating: RatingShow,
    @ColumnInfo("status") val status: ShowStatus,
    @ColumnInfo("summary") val summary: String,
    @ColumnInfo("url") val url: String,
) {
    data class ImageShow(
        @ColumnInfo("url") val medium: String,
        @ColumnInfo("url") val original: String,
    )

    data class NetworkShow(
        @ColumnInfo("url") val country: CountryShow,
        @ColumnInfo("url") val id: Int,
        @ColumnInfo("url") val name: String,
        @ColumnInfo("url") val officialSite: String
    )

    data class CountryShow(
        @ColumnInfo("url") val code: String,
        @ColumnInfo("url") val name: String,
        @ColumnInfo("url") val timezone: String
    )

    data class RatingShow(
        @ColumnInfo("url") val average: Double,
    )

}

sealed class ShowStatus(val statusName: String) {
    object Running : ShowStatus("Running")
    object Ended : ShowStatus("Ended")
    object Determined : ShowStatus("To Be Determined")
    object Unknown : ShowStatus("Unknown")
}