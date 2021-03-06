package com.example.sunnyweatherkt.logic.network

import android.util.Log
import com.example.sunnyweatherkt.logic.model.Weather
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetwork {                                                                        //构建一个 统一网络数据源访问入口
    private val placeService = ServiceCreator.create<PlaceService>()                                        //访问Retrofit构建器，返回retrofit 的构建接口(PlaceService)的动态管理对象，对象就可以访问自己的方法 searchPlaces
    private val weatherService= ServiceCreator.create<WeatherService>()
    const val TAG="WeatherResult"

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()                //placeService.searchPlaces(query),访问接口， placeSerive.searchPlaces(query) 返回Call<PlaceResponsing.PlaceResponse>, 因此可以 调用await
    suspend fun getRealTimeWeather(lng:String,lat:String)= weatherService.getRealTimeWeather(lng,lat).await()
    suspend fun getDailyWeather(lng: String,lat: String)= weatherService.getDailyWeather(lng,lat).await()
    suspend fun getHourlyWeather(lng:String,lat:String)=weatherService.getHourlyWeather(lng,lat).await()


//suspend fun searchPlaces(query:String):PlaceResponse.PlaceResponse{
//    return placeService.searchPlaces(query).await()
//}

//    private suspend fun <T> Call<T>.await():T{
//        return withContext(Dispatchers.IO)(
//            enqueue(object : Callback<T> {block:String->
//
//                override fun onResponse(call: Call<T>, response: Response<T>) {
//                    val body = response.body()
//                    if (body != null) block.resume(body)
//                    else continuation.resumeWithException(
//                            RuntimeException("response body is null")
//                    )
//                }
//                override fun onFailure(call: Call<T>, t: Throwable) {
//                }
//            })
//        )
//    }

    private suspend fun <T> Call<T>.await(): T {                                                    //写了个 await 的扩展函数， Call 的对象可以访问他,并且里面有 Call 的对象语境，可以调用 enqueue
        Log.d(TAG, "await: before" +
                " suspendCoroutine"+Thread.currentThread().name)                                    //在子线程，因为call 他的函数在子线程
        return suspendCoroutine { continuation ->                                                   //简化回调
            Log.d(TAG, "await: in suspendCoroutine"+Thread.currentThread().name)               //子线程，在子线程，因为call 他的函数在子线程
            enqueue(object : Callback<T> {                                                          //匿名内部类 object: Callback<T>,写回调的方法， onResponse, onFailure
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()                                                      //回归主线程
                    Log.d(TAG, "await in callback "+Thread.currentThread().name)
                    if (body != null) continuation.resume(body)                                     //返回body 值
                    else continuation.resumeWithException(
                            RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            }
            )
        }
    }
}




