package com.example.sunnyweatherkt.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.sunnyweatherkt.MyApplication
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.network.SunnyWeatherNetwork
import com.google.gson.Gson

object PlaceDao {                                                                                   //单例类
    private fun sharePreferences()=MyApplication.context.getSharedPreferences("sunny_weather",Context.MODE_PRIVATE)

    fun savePlace(place: PlaceResponsing.Place) {
            sharePreferences().edit() {
            putString("place", Gson().toJson(place))
        }

    }
    fun getSavedPlace():PlaceResponsing.Place{
        val placeJson= sharePreferences().getString("place","")
        return Gson().fromJson(placeJson, PlaceResponsing.Place::class.java)
    }

    fun isPlaceSaved()= sharePreferences().contains("place")

}