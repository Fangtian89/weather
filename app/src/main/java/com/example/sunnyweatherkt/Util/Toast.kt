package com.example.sunnyweatherkt.Util

import android.util.Log
import android.widget.Toast
import com.example.sunnyweatherkt.MyApplication

val k=5
fun String.showToastSt(duration:Int=Toast.LENGTH_SHORT){
    Toast.makeText(MyApplication.context,this,duration).show()
}

fun String.showToastLg(duration:Int=Toast.LENGTH_LONG){
    Toast.makeText(MyApplication.context,this,duration).show()
}

fun test(){
    Log.d("TAG", "test: ")}

