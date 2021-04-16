package com.example.sunnyweatherkt.logic.network

import com.example.sunnyweatherkt.logic.model.DailyResponse
import com.example.sunnyweatherkt.logic.model.RealTimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
//    https://api.caiyunapp.com/v2.5/ggepE823AvATQ6dj/121.6544,25.1552/realtime.json                //current weather
//      https://api.caiyunapp.com/v2.5/ggepE823AvATQ6dj/121.6544,25.1552/daily.json

    @GET("/v2.5/${com.example.sunnyweatherkt.MyApplication.TOKEN}/{lng},{lat}/realtime.json?lang=en")
    fun getRealTimeWeather(@Path("lng") lng:String, @Path("lat") lat:String): Call<RealTimeResponse>
    @GET("/v2.5/${com.example.sunnyweatherkt.MyApplication.TOKEN}/{lng},{lat}/daily.json?lang=en")
    fun getDailyWeather(@Path("lng") lng:String, @Path("lat") lat:String): Call<DailyResponse>

}