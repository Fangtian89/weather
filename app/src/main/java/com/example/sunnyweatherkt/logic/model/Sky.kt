package com.example.sunnyweatherkt.logic.model

import com.example.sunnyweatherkt.R

class Sky(val info:String,val icon:Int,val bg:Int)

    private val sky= mapOf<String,Sky>(                                                             //sky 是一个 map
        "CLEAR_DAY" to Sky("Clear", R.drawable.ic_clear_day,R.mipmap.bg_clear_day),                                                                            //key to value
        "CLEAR_NIGHT" to Sky("Clear Night",R.drawable.ic_clear_night,R.mipmap.bg_clear_night),
        "PARTLY_CLOUDY_DAY" to Sky("Partly Cloudy",R.drawable.ic_partly_cloud_day,R.mipmap.bg_partly_cloudy_day),
        "PARTLY_CLOUDY_NIGHT" to Sky("Partly Cloudy",R.drawable.ic_partly_cloud_night,R.mipmap.bg_partly_cloudy_night),
        "CLOUDY" to Sky("Cloudy",R.drawable.ic_cloudy,R.mipmap.bg_cloudy),
        "WIND" to Sky("Wind",R.drawable.ic_wind,R.mipmap.bg_wind),
        "LIGHT_RAIN" to Sky("Light Rain",R.drawable.ic_light_rain,R.mipmap.bg_rain),
        "MODERATE_RAIN" to Sky("Moderate Rain",R.drawable.ic_moderate_rain,R.mipmap.bg_rain),
        "HEAVY_RAIN" to Sky("Heavy Rain",R.drawable.ic_heavy_rain,R.mipmap.bg_rain),
        "STORM_RAIN" to Sky("Storm Rain",R.drawable.ic_storm_rain,R.mipmap.bg_rain),
        "THUNDER_SHOWER" to Sky("Thunder Rain",R.drawable.ic_thunder_rain,R.mipmap.bg_rain),
        "SLEET" to Sky("Sleet",R.drawable.ic_sleet,R.mipmap.bg_rain),
        "LIGHT_SNOW" to Sky("Light Snow",R.drawable.ic_light_snow,R.mipmap.bg_snow),
        "MODERATE_SNOW" to Sky("Moderate Snow",R.drawable.ic_moderate_snow,R.mipmap.bg_snow),
        "HEAVY_SNOW" to Sky("Heavy Snow",R.drawable.ic_heavy_snow,R.mipmap.bg_snow),
        "STORM_SNOW" to Sky("Storm",R.drawable.ic_heavy_snow,R.mipmap.bg_snow),
        "HAIL" to Sky("Hail",R.drawable.ic_hail,R.mipmap.bg_snow),
        "LIGHT_HAZE" to Sky("Light Haze",R.drawable.ic_light_haze,R.mipmap.bg_fog),
        "MODERATE_HAZE" to Sky("Moderate Haze",R.drawable.ic_moderate_haze,R.mipmap.bg_fog),
        "HEAVY_HAZE" to Sky("Heavy Haze",R.drawable.ic_moderate_haze,R.mipmap.bg_fog),
        "FOG" to Sky("Foggy",R.drawable.ic_fog,R.mipmap.bg_fog),
        "DUST" to Sky("Dusty",R.drawable.ic_dust,R.mipmap.bg_fog)
    )

    fun getSky(skycon:String):Sky{
        return sky[skycon]?:sky["CLEAR_DAY"]!!                                                          //若 sky[skycon] 不为空，返回 sky[skycon]， 为空返回 sky["Clear"], 整体为返回一个 Sky 对象
    }                                                                                                   //skycon is key
