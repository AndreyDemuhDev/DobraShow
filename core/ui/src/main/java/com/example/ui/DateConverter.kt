package com.example.ui

fun dateConverter(date: String): String {
    val year = date.substring(0, 4)
    var month = date.substring(5..6).lowercase()
    val day = date.substring(5..6).lowercase()
    when (month) {
        "01" -> {
            month = "January"
        }

        "02" -> {
            month = "February"
        }

        "03" -> {
            month = "March"
        }

        "04" -> {
            month = "April"
        }

        "05" -> {
            month = "May"
        }

        "06" -> {
            month = "June"
        }

        "07" -> {
            month = "July"
        }

        "08" -> {
            month = "August"
        }

        "09" -> {
            month = "September"
        }

        "10" -> {
            month = "October"
        }

        "11" -> {
            month = "November"
        }

        "12" -> {
            month = "December"
        }
    }
    return "$day $month $year"
}
