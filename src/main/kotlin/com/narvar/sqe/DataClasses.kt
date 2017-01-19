package com.narvar.sqe

data class NumbersHolder(val tracking_numbers: List<NumbersObject>)
data class NumbersObject(val status: String, val carrier_name: String, val values: List<String>)
