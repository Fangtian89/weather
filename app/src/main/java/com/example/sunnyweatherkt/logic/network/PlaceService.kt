package com.example.sunnyweatherkt.logic.network

//import com.example.sunnyweatherkt.logic.model.PlaceResponse
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceService {

    //  https://api.caiyunapp.com/v2/place?query=munich&token=ggepE823AvATQ6dj&lang=zh

    @GET("/v2/place?query&token=${com.example.sunnyweatherkt.MyApplication.TOKEN}&lang=en")
    fun searchPlaces(@Query("query") query:String): Call<PlaceResponsing.PlaceResponse>        //有一个返回值 类型是内置类型Call, 指定服务器响应的数据转换成 PlaceResponse 类型

    @POST()
    fun sendPlaces():Call<ResponseBody>                                                              //如果没有返回值，用ResponseBody
}
