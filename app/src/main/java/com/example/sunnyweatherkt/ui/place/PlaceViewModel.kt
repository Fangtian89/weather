package com.example.sunnyweatherkt.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaceViewModel : ViewModel() {
    private val searchLiveData=MutableLiveData<String>()
    val placeList=ArrayList<PlaceResponsing.Place>()


    val placeLiveData=Transformations.switchMap(searchLiveData){                                    //当 searchLiveData 通过下面的函数赋值或改变值，则被switchMap 观察，并把回值赋给 plaveLiveData (LiveData)，成为可以被activirty 观察的livedata，
        query-> Repository.searchPlaces(query)                                                      //返回一个livedata,对外不可变，返回值为  LiveData<Result<List<PlaceResponsing.Place>
    }

    fun searchPlacesViewModel(query:String){
        searchLiveData.value=query
    }

    fun savedPlace(place:PlaceResponsing.Place)=Repository.savePlace(place)
    fun getSavedPlace()=Repository.getSavedPlace()
    fun isSavedPlace()=Repository.isPlaceSaved()

}