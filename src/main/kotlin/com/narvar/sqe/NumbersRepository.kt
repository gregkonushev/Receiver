package com.narvar.sqe

interface NumbersRepository {
  fun saveNumbers(numbers: TrackingNumbers)
  fun getAllNumbers()
}