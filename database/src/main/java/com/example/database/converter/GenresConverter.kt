package com.example.database.converter

import androidx.room.TypeConverter
import java.util.Arrays
import java.util.stream.Collectors


class GenresConverter {
    @TypeConverter
    fun fromListGenres(genres: List<String?>): String {
        return genres.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toListGenres(data: String): List<String> {
        return Arrays.asList(*data.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray())
    }
}