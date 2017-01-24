package com.narvar.sqe.pojo

interface NumbersRepository {
  fun saveNumbers(numbers: TrackingNumbers)
  fun getAllNumbers(): Numbers
  fun getNumbersByCarrierAndStatus(parameters: QueryParameters): Numbers
}