package com.example.sunnyweatherkt.ui.favourite

import android.content.Context
import android.os.Bundle
import androidx.core.content.contentValuesOf
import androidx.lifecycle.liveData
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.model.RealTimeResponse
import com.example.sunnyweatherkt.logic.network.SunnyWeatherNetwork
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

class FavouriteWorker(context: Context, workerParams: WorkerParameters) :Worker(context,workerParams) {

    override fun doWork():Result{
//        return liveData{
//            val data=inputData.getString()
//            val place=Gson().fromJson(data,PlaceResponsing.Place::class.java)
//            val lng=place.location.lng
//            val lat=place.location.lat
//            val result=try{
//                coroutineScope {
//                    val weatherResult= async {SunnyWeatherNetwork.getRealTimeWeather(lng,lat)}.await()
//                    if("ok"==weatherResult.status){
//                       Result.success(weatherResult)
//                    }else{
//                       Result.failure()
//                    }
//                    Result.success()
//                }
//
//            }catch (e:Exception){
//                Result.failure()
//            }
        return Result.success()
        }
}

