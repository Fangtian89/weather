package com.example.sunnyweatherkt.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.model.PlaceResponsing

class FavouriteViewModel:ViewModel() {

    private val locationLiveData=MutableLiveData<MutableMap<String,PlaceResponsing.Place>>()

    val weatherLiveData=Transformations.switchMap(locationLiveData){placelist->
        Repository.favouriteWeahterRefresh(placelist)
    }


    fun refreshFavouriteWeather(placelist:MutableMap<String,PlaceResponsing.Place>?){
        locationLiveData.value=placelist
    }


    fun saveFavouritePlace(place:PlaceResponsing.Place){
        Repository.saveFavouritePlace(place)
    }

    fun readFavouritePlace()=Repository.readFavouritePlace()
}