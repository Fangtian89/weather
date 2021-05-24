package com.example.sunnyweatherkt.ui.weather

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.model.Weather

class WeatherViewModel:ViewModel() {
    val locationLiveData=MutableLiveData<PlaceResponsing.Location>()
    var locationLng=""
    var locationLat=""
    var placeName=""

    val weatherLiveData=Transformations.switchMap(locationLiveData){
        location->

        Repository.refreshWeather(location.lng,location.lat)                              //从外部获取的LiveData 实例
    }

    fun refreshWeather(lng:String,lat:String){
        locationLiveData.value=PlaceResponsing.Location(lng,lat)
    }
}