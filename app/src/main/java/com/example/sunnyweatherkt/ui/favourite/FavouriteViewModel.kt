package com.example.sunnyweatherkt.ui.favourite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.model.RealTimeResponse
import com.example.sunnyweatherkt.logic.model.Weather

class FavouriteViewModel:ViewModel() {

    private val locationLiveData=MutableLiveData<PlaceResponsing.Location>()
    val TAG="WeatherResult"
    val favouriteWeatherLiveData=Transformations.switchMap(locationLiveData){location->
        Repository.favouriteWeatherRefresh(location.lng,location.lat)
    }


    fun refreshFavouriteWeather(lng:String,lat:String){
        locationLiveData.postValue(PlaceResponsing.Location(lng,lat))
        Log.d(TAG, "refreshFavouriteWeather: "+locationLiveData+" "+lng+" "+lat+" "+Thread.currentThread().name)

    }


    fun saveFavouritePlace(place:PlaceResponsing.Place,weather:Weather){
        Repository.saveFavouritePlace(place,weather)
    }

    fun readFavouritePlace()=Repository.readFavouritePlace()

}