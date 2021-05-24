package com.example.sunnyweatherkt.ui.place

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MyObserver(val lifecycle: Lifecycle):LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun activityStart(){
        Log.d("WeatherResult", "activityStart: from Observer")}

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun activityStop(){
        Log.d("WeatherResult", "activityStop: from Observer")}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun activityResume(){
        Log.d("WeatherResult", "activityResume: from Observer")}

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun activityPause(){
        Log.d("WeatherResult", "activityPause: from Observer")}

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun activityCreate(){
        Log.d("WeatherResult", "activityCreate: from Observer")}

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun activityDestroy(){
        Log.d("WeatherResult", "activityDestroy: from Observer")}
}