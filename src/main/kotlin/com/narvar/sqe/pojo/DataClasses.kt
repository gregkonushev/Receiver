package com.narvar.sqe.pojo

data class Numbers(val tracking_numbers: MutableList<TrackingNumbers>)
data class TrackingNumbers(val status: String?, val carrier: String?, val partner_carrier: String?, val values: MutableList<String>?) {
  val key: String
    get() = "$carrier:$partner_carrier:${status?.toLowerCase()}"
}

data class StandardResponse(val status_code: Int, val message: String)

data class QueryParameters(val carrier: String?, val status: String?, val partner_carrier: String?) {
  val key: String
    get() = "$carrier:$partner_carrier:${status?.toLowerCase()}"
}

fun String.getStatus() = this.substringAfterLast(":")
fun String.getCarrier() = this.substringBefore(":")
fun String.getPartnerCarrier() = this.substringBeforeLast(":").substringAfter(":")

