package com.example.network


sealed class ShowStatus(val statusName: String) {
    object Running: ShowStatus("Running")
    object Ended: ShowStatus("Ended")
    object Determined: ShowStatus("To Be Determined")
    object Unknown: ShowStatus("Unknown")
}