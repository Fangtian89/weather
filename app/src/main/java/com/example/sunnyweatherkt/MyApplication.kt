package com.example.sunnyweatherkt

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.Toast

class MyApplication: Application() {
    @SuppressLint("StaticFieldLeak")
    companion object{                                                                               //้ๆๆจกๅ
        lateinit var context: Context
        const val TOKEN="ggepE823AvATQ6dj"
    }

    override fun onCreate() {
        super.onCreate()

        context =applicationContext
    }
    fun getValue( value:String):String{
        return value
    }
}