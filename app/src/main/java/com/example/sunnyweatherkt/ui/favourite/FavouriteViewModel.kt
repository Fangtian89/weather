package com.example.sunnyweatherkt.ui.favourite

import android.util.Log
import androidx.lifecycle.*
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.model.RealTimeResponse
import com.example.sunnyweatherkt.logic.model.Weather
import kotlinx.coroutines.delay

class FavouriteViewModel:ViewModel() {

    private val locationLiveData=MutableLiveData<PlaceResponsing.Location>()
    val TAG="WeatherResult"



//    val favouriteWeatherLiveData=Transformations.switchMap(locationLiveData){location->
//        Repository.favouriteWeatherRefresh(location.lng,location.lat)
//    }
//
//
//     fun  refreshFavouriteWeather(lng:String,lat:String){
//         locationLiveData.postValue(PlaceResponsing.Location(lng,lat))
//         Log.d(TAG, "refreshFavouriteWeather")
//     }
//
//
//    fun saveFavouritePlace(place:PlaceResponsing.Place,weather:Weather){
//        Log.d(TAG, "saved")
//        Repository.saveFavouritePlace(place,weather)
//    }

    fun readFavouritePlace()=Repository.readFavouritePlace()

    fun removeFavouritePlace(key:PlaceResponsing.Place)=Repository.removeFavouritePlace(key)
}