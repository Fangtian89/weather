package com.example.sunnyweatherkt.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.sunnyweatherkt.MyApplication
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.model.RealTimeResponse
import com.example.sunnyweatherkt.logic.model.Weather
import com.google.gson.Gson

object FavouriteDao {
    fun sharedPreference()=MyApplication.context.getSharedPreferences("Favourite",Context.MODE_PRIVATE)

    fun saveFavouritePlace(place: PlaceResponsing.Place,weather:Weather){
        sharedPreference().edit() {
            putString(Gson().toJson(place),Gson().toJson(weather))
        }
    }

    fun readFavouritePlace(): MutableMap<String, String> {

        val result:MutableMap<String,String>
        @Suppress("UNCHECKED_CAST")
        result=sharedPreference().all as MutableMap<String, String>
        return result
    }
}

