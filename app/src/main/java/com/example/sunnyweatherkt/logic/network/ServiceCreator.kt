package com.example.sunnyweatherkt.logic.network


import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ServiceCreator {                                                                             //构建一个Retrofit构建器，用来使用或访问接口

    private const val BASE_URL="https://api.caiyunapp.com/"

    private val retrofit= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //inline fun<reified T>create(serviceClass:Class<T>):T= retrofit.create(serviceClass)               //也可以只用这一行，需要把retrofit 的private 去掉
//  val appServices= retrofit.create(PlaceService::class.java)
    fun <T> create(serviceClass:Class<T>):T = retrofit.create(serviceClass)                          //Class<T>意思是 T 型的 Class类，retrofit 接收一个 T Class
//    fun <T>create(servieClass:Class<T>):T{
//        return retrofit.create(servieClass)
//    }
//
    inline fun<reified T> create():T= create(T::class.java)                                         //T::class.java 意思是 T 型的Class 类对象

//    inline fun<reified T> create():T{
//        return create(T::class.java)
    }




