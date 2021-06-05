package com.example.sunnyweatherkt.ui.favourite

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import com.example.sunnyweatherkt.Util.showToastSt
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.model.Weather
import com.example.sunnyweatherkt.logic.network.SunnyWeatherNetwork
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.lang.RuntimeException
import kotlin.coroutines.suspendCoroutine

class MyService: IntentService("MyService") {                                                //以后台service的功能更新及保存喜爱的地方
    val TAG="WeatherResult"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "service created")
    }
    override fun onHandleIntent(intent: Intent?) {
//        val job= Job()                                                                          //创建job 对象
//        val scope= CoroutineScope(job)                                                          //创建 CoroutineScope对象
//        scope.launch(Dispatchers.IO) {                                                          //用 CoroutineScope对象开启协程, 不写 Dispatchers.IO也默认是子线程

            CoroutineScope(Dispatchers.IO).launch{
                val placeWeatherList = Repository.readFavouritePlace()
                if(placeWeatherList.isEmpty()){
                    stopSelf()
                }

                var list=ArrayList<PlaceResponsing.Place>()
                lateinit var weatherResult:Weather
                Log.d(TAG, "onHandleWork: 1 "+Thread.currentThread().name+" "+placeWeatherList)
                placeWeatherList.forEach { (t, u) ->
                    Log.d(TAG, "onHandleIntent: hallo world")
                    list.add(Gson().fromJson(t, PlaceResponsing.Place::class.java))                          //取出地址，放到Arraylist 中,  造一个新的list, key是城市名字,value是PlaceResponsing.Place
                }

                list.forEach(){
                    Log.d(TAG, "onHandleWork: 2 "+it.name+" "+Thread.currentThread().name)
                    val deferredRealtime = async{SunnyWeatherNetwork.getRealTimeWeather(it.location.lng,it.location.lat)}           //因为async 需要 协程区域 coroutineScope, async 会阻塞协程，知道得到结果
                    val deferredDaily = async { SunnyWeatherNetwork.getDailyWeather(it.location.lng,it.location.lat) }
                    //同时获取结果
                    val getResultdDaily = deferredDaily.await()
                    val getResultRealtime = deferredRealtime.await()
                    Log.d(TAG, "onHandleWork: 3 "+Thread.currentThread().name)
                    if (getResultdDaily.status .equals("ok") && getResultRealtime.status .equals("ok")) {
                        weatherResult = Weather(getResultRealtime.result.realtime, getResultdDaily.result.daily)        //把2个结果都放在 weather,作为一个结果
                        Log.d(TAG, "onHandleWork: 4 "+Thread.currentThread().name)
                        //if 最后一行表示返回
                    } else {
                        RuntimeException("realtime response status is ${getResultRealtime.status} " +     //else 最后一行表示返回
                                "daily response status is ${getResultdDaily.status}!!")
                    }

                    Log.d(TAG, "onHandleWork: 5 "+Thread.currentThread().name)
                    Repository.saveFavouritePlace(it,weatherResult)
                }
            }

    }



//    override fun onHandleWork(intent: Intent) {
//        Log.d(TAG, "onHandleWork: 1")
//        val placeWeatherList = Repository.readFavouritePlace()
//        var list=ArrayList<PlaceResponsing.Place>()
//        var weatherResult: Weather? = null
//        Log.d(TAG, "onHandleWork: 1")
//        placeWeatherList.forEach { (t, u) ->
//
//            list.add(Gson().fromJson(t, PlaceResponsing.Place::class.java))                          //取出地址，放到Arraylist 中,  造一个新的list, key是城市名字,value是PlaceResponsing.Place
//        }
//        list.forEach(){
//            Thread.sleep(1000)
//            val result=Repository.refreshWeather(it.location.lng,it.location.lat)                    //获取 weather, 得到的结果包装在 Result<LiveData<Weather>> 里面
//            if(result.value!=null){
//                Log.d(TAG, "onHandleWork: 2")
//                Repository.saveFavouritePlace(it,result.value?.getOrNull()!!)
//            }
//        }
//    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d(TAG, "onTaskRemoved: ")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "service destroyed")
    }

}