package com.narvar.sqe.pojo

import com.fasterxml.jackson.annotation.JsonIgnore

data class TrackingNumbers(val carrier: String?, val partner_carrier: String?, val status: String?, val signature: Boolean?, val values: MutableList<String>?) {

  @JsonIgnore
  val key: String = "$carrier:$partner_carrier:${status?.toLowerCase()}:$signature"
}

data class StandardResponse(val status_code: Int?, val message: String?, val tracking_numbers: MutableList<TrackingNumbers>?)

fun String.getCarrier() = this.split(":")[0]
fun String.getPartnerCarrier() = this.split(":")[1]
fun String.getStatus() = this.split(":")[2]
fun String.getSignature(): Boolean? = this.split(":")[3].toBoolean()
