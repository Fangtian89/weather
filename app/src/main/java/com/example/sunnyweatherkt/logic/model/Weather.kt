package com.example.sunnyweatherkt.logic.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

data class Weather(val realTime:RealTimeResponse.RealTime,val dailyResponse:DailyResponse.Daily){

}
