package com.example.sunnyweatherkt.logic.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetwork {                                                                        //构建一个 统一网络数据源访问入口
    val placeService = ServiceCreator.create<PlaceService>()                                        //访问Retrofit构建器，返回retrofit 的构建接口(PlaceService)的动态管理对象，对象就可以访问自己的方法 searchPlaces
    val weatherService= ServiceCreator.create<WeatherService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()                //placeService.searchPlaces(query),访问接口， placeSerive.searchPlaces(query) 返回Call<PlaceResponsing.PlaceResponse>, 因此可以 调用await
    suspend fun getRealTimeWeather(lng:String,lat:String)= weatherService.getRealTimeWeather(lng,lat).await()
    suspend fun getDailyWeather(lng: String,lat: String)= weatherService.getDailyWeather(lng,lat).await()

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
        Log.d("TAGGING", "await: "+Thread.currentThread().name)                                   //子线程
        return suspendCoroutine { continuation ->
            Log.d("TAGGING", "await: "+Thread.currentThread().name)                               //子线程
            enqueue(object : Callback<T> {                                                          //匿名内部类 object: Callback<T>
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    Log.d("TAGGING", "onResponse: "+Thread.currentThread().name)                  //主线程
                    if (body != null) continuation.resume(body)                                     //恢复线程 , 返回body 值
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




