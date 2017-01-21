package com.narvar.sqe

data class Numbers(val tracking_numbers: List<TrackingNumbers>)
data class TrackingNumbers(val status: String, val carrier_name: String, val values: List<String>)

data class StandardResponse(val status_code: Int, val message: String)