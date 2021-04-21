package com.example.sunnyweatherkt.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.model.RealTimeResponse
import com.example.sunnyweatherkt.logic.model.Weather

class FavouriteViewModel:ViewModel() {

    private val locationLiveData=MutableLiveData<ArrayList<PlaceResponsing.Place>>()

    val weatherLiveData=Transformations.switchMap(locationLiveData){placelist->
        Repository.favouriteWeahterRefresh(placelist)
    }


    fun refreshFavouriteWeather(placelist:ArrayList<PlaceResponsing.Place>){
        locationLiveData.value=placelist
    }


    fun saveFavouritePlace(place:PlaceResponsing.Place,weather:Weather){
        Repository.saveFavouritePlace(place,weather)
    }

    fun readFavouritePlace()=Repository.readFavouritePlace()

    fun isPlaceSaved()=Repository.isPlaceSaved()
}