package com.narvar.sqe.pojo

data class Numbers(val tracking_numbers: List<TrackingNumbers>)
data class TrackingNumbers(val status: String?, val carrier: String?, val partner_carrier: String?, val values: List<String>?)

data class StandardResponse(val status_code: Int, val message: String)

data class QueryParameters(val carrier: String?, val status: String?, val partner_carrier: String?)