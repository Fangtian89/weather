package com.example.sunnyweatherkt.logic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.sunnyweatherkt.logic.dao.FavouriteDao
import com.example.sunnyweatherkt.logic.dao.PlaceDao
//import com.example.sunnyweatherkt.logic.model.PlaceResponse
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.model.RealTimeResponse
import com.example.sunnyweatherkt.logic.model.Weather
import com.example.sunnyweatherkt.logic.network.SunnyWeatherNetwork
import com.example.sunnyweatherkt.logic.network.WeatherService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {                                                                                 //仓库，决定访问网络还是 从数据库
    fun searchPlaces(query:String) = liveData(Dispatchers.IO){                                     //使用liveData 返回一个LiveData 对象, 开一个协程，及子线程，并用emit() 返回结果，返回值  LiveData<Result<List<PlaceResponsing.Place
        Log.d("TAGGING", "searchPlaces: "+Thread.currentThread().name)
        val result=try {
            val placeResponse=SunnyWeatherNetwork.searchPlaces(query)                               //网络层访问，得到值即是 SunnyWeatherNetwork里面的response.body()
            if (placeResponse.status=="ok"){
                val place=placeResponse.places
                Result.success(place)}                                                              //返回值是Result.success(place), place值包裹在Result.success 里面
            else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<PlaceResponsing.Place>>(e)
        }
          emit(result)
    }

    fun  refreshWeather(lng:String,lat:String) = liveData(Dispatchers.IO){
            val result=try {
                coroutineScope {
                    val deferredRealtime = async { SunnyWeatherNetwork.getRealTimeWeather(lng, lat) }           //因为async 需要 协程区域 coroutineScope
                    val deferredDaily = async { SunnyWeatherNetwork.getDailyWeather(lng, lat) }
                    //同时获取结果
                    val getResultdDaily = deferredDaily.await()
                    val getResultRealtime = deferredRealtime.await()

                    if (getResultdDaily.status .equals("ok") && getResultRealtime.status .equals("ok")) {
                        val weatherResult = Weather(getResultRealtime.result.realtime, getResultdDaily.result.daily)        //把2个结果都放在 weather,作为一个结果
                        Result.success(weatherResult)                                                       //if 最后一行表示返回
                    } else {
                        Result.failure(RuntimeException("realtime response status is ${getResultRealtime.status} " +     //else 最后一行表示返回
                                "daily response status is ${getResultdDaily.status}!!"))
                    }
                }
            }catch (e:Exception){
                Result.failure<Weather>(e)
            }
            emit(result)
    }


    fun favouriteWeahterRefresh(placelist:MutableMap<String,PlaceResponsing.Place>)= liveData(Dispatchers.IO) {
        var  maps:MutableMap<String,RealTimeResponse> = mutableMapOf()
        val result=try{
            coroutineScope {
                placelist.forEach(){
                    val place= it.value
                    val realTime=async {
                        SunnyWeatherNetwork.getRealTimeWeather(place.location.lng,place.location.lat) }.await()
                        if("ok" == realTime.status){
                            maps.put(it.key,realTime)
                    }else{
                        Result.failure<MutableMap<String,RealTimeResponse>>(RuntimeException("realtime response status is ${realTime.status} "))
                    }
                }
                Result.success(maps)
            }
        }catch (e:Exception){
            Result.failure<MutableMap<String,RealTimeResponse>>(e)
        }
        emit(result)
    }

//    private fun<T> fire(context:CoroutineContext,block:suspend()->Result<T>):LiveData<Result<T>>{
//        return liveData(context) {
//            val result=try{
//                block
//            }catch (e:Exception){
//                Result.failure<T>(e)
//            }
//            emit(result<T>)
//        }
//    }

//    private fun <T> fire(context:CoroutineContext, block : suspend () -> Result<T>)=
//        liveData<Result<T>>(context){
//             val result = try {
//                 block
//             } catch (e: Exception) {
//                 Result.failure<T>(e)
//             }
//            emit(result<*>)
//
//        }

    fun savePlace(place:PlaceResponsing.Place) {
        PlaceDao.savePlace(place)
    }

    fun getSavedPlace()= PlaceDao.getSavedPlace()


    fun isPlaceSaved():Boolean=PlaceDao.isPlaceSaved()


    fun saveFavouritePlace(place:PlaceResponsing.Place){
        FavouriteDao.saveFavouritePlace(place)
    }

    fun readFavouritePlace()=FavouriteDao.readFavouritePlace()

    private fun Gson.fromJson(it1: Any, java: Class<PlaceResponsing.Place>) {

    }

}





